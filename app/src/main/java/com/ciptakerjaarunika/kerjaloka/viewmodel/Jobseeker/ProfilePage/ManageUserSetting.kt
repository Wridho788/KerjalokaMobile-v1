package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentManageProfileSettingLayoutBinding
import com.ciptakerjaarunika.kerjaloka.enum.SocialMediaType
import com.ciptakerjaarunika.kerjaloka.model.Data.socialMedia
import com.ciptakerjaarunika.kerjaloka.model.User.GoogleLoginRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Components.ModalDeactivateAccount
import com.ciptakerjaarunika.kerjaloka.viewmodel.LoginPage.Login
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.UserSetting.EditEmail
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.UserSetting.EditPassword
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.UserSetting.EditPhone
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.UserSetting.EditUserName
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class ManageUserSetting : Fragment() {
    private lateinit var binding: FragmentManageProfileSettingLayoutBinding
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
        binding = FragmentManageProfileSettingLayoutBinding.inflate(layoutInflater)
        val view = binding.root

        if (SessionManager(context).user != null) {
            val user = SessionManager(context).user

            binding.editEmailProfileSetting.setOnClickListener {
                editEmailFragment(user?.email)
            }
            binding.editNomorTeleponSetting.setOnClickListener {
                editPhoneFragment(user?.phone)
            }
            binding.profileUsername.text = user?.username
            binding.profileEmail.text = user?.email
            binding.profileNomorTelepon.text = user?.phone
        }

        binding.editUsernameSetting.setOnClickListener {
            replaceFragment(EditUserName())
        }
        binding.editKataSandi.setOnClickListener {
            replaceFragment(EditPassword())
        }

        binding.btnLogout.setOnClickListener {
            ProfileAPI().Logout(SessionManager(context).device_token, context) {
                val intent = Intent(context, MainActivity()::class.java)
                startActivity(intent)
            }
        }



        binding.btnNonaktifkanAkun.setOnClickListener {
            val sheet = ModalDeactivateAccount()
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }
        getProfileData()
        return view
    }

    fun getProfileData() {
        ProfileAPI().JobseekerGetProfileData(context) {
            if (it != null) {

                if (it.data.users.userGoogleId.isNullOrEmpty()) {
                    binding.connect.strokeColor =
                        ColorStateList.valueOf(Color.parseColor("#FF6666"))
                    binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6666")))
                    binding.connect.setOnClickListener { signIn() }
                    binding.connect.text = "Hubungkan"
                } else {
                    binding.connect.strokeColor =
                        ColorStateList.valueOf(Color.parseColor("#FFDEDE"))
                    binding.connect.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFDEDE")))
                    binding.connect.isClickable = false
                    binding.connect.text = "Terkoneksi"
                }

                binding.switchDiscoverable.isChecked = it.data.users.isDiscoverable ?: false
                binding.switchNewsLetter.isChecked = it.data.users.isNewsletter ?: false

                binding.switchNewsLetter.setOnClickListener { it1 ->
                    if (binding.switchNewsLetter.isChecked == true) {
                        setNewsletter = true
                        company_profile_api().newsletter(setNewsletter, context) {}
                    } else {
                        setNewsletter = false
                        company_profile_api().newsletter(setNewsletter, context) {}
                    }
                }
                binding.switchDiscoverable.setOnClickListener {
                    if (binding.switchDiscoverable.isChecked == true) {
                        company_profile_api().discoverable(context) {}
                    } else {
                        company_profile_api().undiscoverable(context) {}
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
        view?.findViewById<LinearLayout>(R.id.spinnerLogin)?.visibility = View.VISIBLE
        if (requestCode == AkunPage.Req_Code) {
            try {
                view?.findViewById<LinearLayout>(R.id.spinnerLogin)?.visibility = View.GONE
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
            Log.d("err", "handleSignInResult:" + e.toString())
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
                Log.d("Crypt", hash)

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
                Log.d("googleRequest", googleRequest.toString())
                ProfileAPI().GetSocialMediaCheck(context) {
                    Log.d("SocialMedia", it.toString())
                    if (it != null) {
                        if (it.google == true) {
                            Toast.makeText(
                                context,
                                "Akun Google Sudah Terdaftar pada Jobseeker Lain",
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
                                if (it != null) {
                                    Log.d("SocialMedia", it.toString())
                                    Toast.makeText(
                                        context,
                                        it.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
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


    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }


    private fun editEmailFragment(data: String?) {
        val editEmailFragment = EditEmail()
        val mBundle = Bundle()
        val data = Gson().toJson(data)
        mBundle.putString(EditEmail.EXTRA_USER_DATA, data)
        editEmailFragment.arguments = mBundle
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container,
                editEmailFragment,
                EditEmail::class.java.simpleName
            )
            addToBackStack(null)
            commit()

        }
    }

    private fun editPhoneFragment(data: String?) {
        val user = SessionManager(context).user
        val editPhoneFragment = EditPhone(user?.phone!!)
        val mBundle = Bundle()
        val data = Gson().toJson(data)
        mBundle.putString(EditPhone.EXTRA_USER_DATA, data)
        editPhoneFragment.arguments = mBundle
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container,
                editPhoneFragment,
                EditPhone::class.java.simpleName
            )
            addToBackStack(null)
            commit()
        }
    }
}