package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Setting

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.api.users
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentAccountSettingBinding
import com.ciptakerjaarunika.kerjaloka.enum.SocialMediaType
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.model.Data.socialMedia
import com.ciptakerjaarunika.kerjaloka.model.User.GoogleLoginRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.data
import com.ciptakerjaarunika.kerjaloka.viewmodel.Components.ModalDeactivateAccount
import com.ciptakerjaarunika.kerjaloka.viewmodel.LoginPage.Login
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*


class AccountSetting(var data: data?) : Fragment(), iRefreshData {
    private lateinit var binding: FragmentAccountSettingBinding

    var setNewsletter: Boolean = false

    companion object {
        var mGoogleSignInClient: GoogleSignInClient? = null
        private var mAuth: FirebaseAuth? = null
        val Req_Code: Int = 123
        val firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        users().CompanyGetUserData(context) {
            if (it != null) {
                if (it.code == 200) {
                    binding.switchDiscoverable.isChecked = it.data.isDiscoverable
                    binding.switchNewsLetter.isChecked = it.data.isNewsletter
                    if (it.data.userGoogleId.isNullOrEmpty()) {
                        binding.connect.strokeColor =
                            ColorStateList.valueOf(Color.parseColor("#FF6666"))
                        binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6666")))
                        binding.connect.setOnClickListener {
                            signIn()
                        }
                        binding.connect.text = "Hubungkan"
                    } else {
                        binding.connect.strokeColor =
                            ColorStateList.valueOf(Color.parseColor("#FFDEDE"))
                        binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFDEDE")))
                        binding.connect.isClickable = false
                        binding.connect.text = "Terkoneksi"
                    }
                    binding.switchDiscoverable.setOnClickListener {
                        if (binding.switchDiscoverable.isChecked) {
                            company_profile_api().discoverable(context) {}
                        } else {
                            company_profile_api().undiscoverable(context) {}
                        }
                    }

                    binding.switchNewsLetter.setOnClickListener { it1 ->
                        if (binding.switchNewsLetter.isChecked == true) {
                            setNewsletter = true
                            company_profile_api().newsletter(setNewsletter, context) {}
                        } else {
                            setNewsletter = false
                            company_profile_api().newsletter(setNewsletter, context) {}
                        }
                    }
                    if (it.data.phone != null) {
                        var phone = it.data.phone.toString()
                        binding.editNomorTeleponSetting.setOnClickListener {
                            replaceFragment(CompEditPhone(this, phone))
                        }
                    }

                    var email = it.data.email.toString()
                    binding.editEmailProfileSetting.setOnClickListener {
                        replaceFragment(CompEditEmail(this, email))
                    }
                }
            }
        }


        binding.btnLogout.setOnClickListener {
            ProfileAPI().Logout(SessionManager(context).device_token, context) {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }
        binding.editUsernameSetting.setOnClickListener {
            replaceFragment(CompEditUsername(this))
        }

        binding.editKataSandi.setOnClickListener {
            replaceFragment(CompEditKataSandi(this))
        }


        binding.btnDeactivedAcc.setOnClickListener {
            val sheet = ModalDeactivateAccount()
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }
        refresh()
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.addToBackStack("")
        fragmentTransaction?.commit()
    }

    override fun refresh() {
        company_profile_api().CompanyGetProfileData(context) {
            binding.spinner.visibility = GONE
            if (it != null) {
                data = it.data
                binding.profileUsername.text = data?.username
                binding.profilePhone.text = data?.phone
                binding.profileEmail.text = data?.email
                binding.compProfileAddress.text = data?.companyAddress

                users().CompanyGetUserData(context) {
                    binding.switchDiscoverable.isChecked = it?.data?.isDiscoverable!!
                    binding.switchNewsLetter.isChecked = it.data.isNewsletter
                    if (it.data.userGoogleId.isNullOrEmpty()) {
                        binding.connect.strokeColor =
                            ColorStateList.valueOf(Color.parseColor("#FF6666"))
                        binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6666")))
                        binding.connect.text = "Hubungkan"
                        binding.connect.setOnClickListener {
                            signIn()
                        }
                    } else {
                        binding.connect.strokeColor =
                            ColorStateList.valueOf(Color.parseColor("#FFDEDE"))
                        binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFDEDE")))
                        binding.connect.isClickable = false
                        binding.connect.text = "Terkoneksi"
                    }
                    binding.switchDiscoverable.setOnClickListener {
                        if (binding.switchDiscoverable.isChecked) {
                            company_profile_api().discoverable(context) {}
                        } else {
                            company_profile_api().undiscoverable(context) {}
                        }
                    }


                    binding.switchNewsLetter.setOnClickListener { it1 ->
                        if (binding.switchNewsLetter.isChecked == true) {
                            setNewsletter = true
                            company_profile_api().newsletter(setNewsletter, context) {}
                        } else {
                            setNewsletter = false
                            company_profile_api().newsletter(setNewsletter, context) {}
                        }
                    }
                }


                binding.btnLogout.setOnClickListener {
                    ProfileAPI().Logout(SessionManager(context).device_token, context) {
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                binding.editUsernameSetting.setOnClickListener {
                    replaceFragment(CompEditUsername(this))
                }
                binding.editEmailProfileSetting.setOnClickListener {
                    replaceFragment(CompEditEmail(this, data?.email.toString()))
                }
                binding.editKataSandi.setOnClickListener {
                    replaceFragment(CompEditKataSandi(this))
                }
                binding.editNomorTeleponSetting.setOnClickListener {
                    replaceFragment(CompEditPhone(this, data?.phone.toString()))
                }

                binding.btnDeactivedAcc.setOnClickListener {
                    val sheet = ModalDeactivateAccount()
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent: Intent = AkunPage.mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, AkunPage.Req_Code)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.spinner.visibility = VISIBLE
        if (requestCode == AkunPage.Req_Code) {
            try {
                binding.spinner.visibility = GONE
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Google Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val currentDate = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = currentDate
        cal.add(Calendar.HOUR, +1)
        val oneHourBack: Date = cal.time

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Login.SavedPreference.setEmail(context!!, account.email.toString())
                Login.SavedPreference.setUsername(context!!, account.displayName.toString())

                val text = "${account.idToken}${config().authKey}${4}"
                val crypt = MessageDigest.getInstance("MD5")
                crypt.update(text.toByteArray())
                val hash = BigInteger(1, crypt.digest()).toString(16)
                fun md5(str: String): ByteArray =
                    MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    val token = task.result
                    SessionManager(context).device_token = token
                })

                val googleRequest =
                    GoogleLoginRequest(
                        account.idToken.toString(),
                        oneHourBack.toString(),
                        hash,
                        deviceToken = SessionManager(context).device_token
                    )
                ProfileAPI().GetSocialMediaCheck(context) {
                    if (it != null) {
                        if (it.google == true) {
                            Toast.makeText(
                                context,
                                "Akun Google Sudah Terdaftar pada Company Lain",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            val user = SessionManager(context).user?.userNo
                            val socialMedia = socialMedia(
                                socialMediaConnectionNo = null,
                                userNo = user!!,
                                socialMediaNo = SocialMediaType.Google.value,
                                accessToken = account.idToken.toString()
                            )
                            ProfileAPI().AddSocialMedia(context, socialMedia) {
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan yang tidak diketahui",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}