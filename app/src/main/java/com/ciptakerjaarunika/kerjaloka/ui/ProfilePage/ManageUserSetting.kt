package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.enum.SocialMediaType
import com.ciptakerjaarunika.kerjaloka.model.Data.socialMedia
import com.ciptakerjaarunika.kerjaloka.model.User.GoogleLoginRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.ui.Global.ModalDeactivateAccount
import com.ciptakerjaarunika.kerjaloka.ui.LoginPage.Login
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditEmail
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditPassword
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditPhone
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.UserSetting.EditUserName
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class ManageUserSetting : Fragment() {

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
        val view =
            inflater.inflate(R.layout.fragment_manage_profile_setting_layout, container, false)
        val username = view.findViewById<TextView>(R.id.profile_username)
        val email = view.findViewById<TextView>(R.id.profile_email)
        val phone = view.findViewById<TextView>(R.id.profile_nomor_telepon)
        val password = view.findViewById<TextView>(R.id.profile_kata_sandi)
        val editUserName = view.findViewById<TextView>(R.id.edit_username_setting)
        val editEmail = view.findViewById<TextView>(R.id.edit_email_profile_setting)
        val editPhone = view.findViewById<TextView>(R.id.edit_nomor_telepon_setting)
        val editPassword = view.findViewById<TextView>(R.id.edit_kata_sandi)
        val discover = view.findViewById<Switch>(R.id.switchDiscoverable)
        val newsletter = view.findViewById<Switch>(R.id.switchNewsLetter)
        val btnDeactive = view.findViewById<MaterialButton>(R.id.btn_nonaktifkan_akun)
        val btnConnectGoogle = view.findViewById<MaterialButton>(R.id.connect)

        val user = SessionManager(context).user

        editEmail.setOnClickListener {
            editEmailFragment(user?.email)
        }
        editUserName.setOnClickListener {
            replaceFragment(EditUserName())
        }
        editPhone.setOnClickListener {
            editPhoneFragment(user?.phone)
        }
        editPassword.setOnClickListener {
            replaceFragment(EditPassword())
        }

        view.findViewById<MaterialButton>(R.id.btn_logout).setOnClickListener {
            ProfileAPI().Logout(SessionManager(context).device_token, context) {
                val intent = Intent(context, MainActivity()::class.java)
                startActivity(intent)
            }
        }

        ProfileAPI().JobseekerGetProfileData(context) {
            if (it != null) {

                if (it.data.users.userGoogleId.isNullOrEmpty()) {
                    btnConnectGoogle.strokeColor =
                        ColorStateList.valueOf(Color.parseColor("#FF6666"))
                    btnConnectGoogle.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6666")))
                    btnConnectGoogle.setOnClickListener { signIn() }
                    btnConnectGoogle.text = "Hubungkan"
                } else {
                    btnConnectGoogle.strokeColor =
                        ColorStateList.valueOf(Color.parseColor("#FFDEDE"))
                    btnConnectGoogle.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFDEDE")))
                    btnConnectGoogle.isClickable = false
                    btnConnectGoogle.text = "Terkoneksi"
                }

                discover.isChecked = it.data.users.isDiscoverable ?: false
                newsletter.isChecked = it.data.users.isNewsletter ?: false

                newsletter.setOnClickListener { it1 ->
                    if (newsletter.isChecked == true) {
                        setNewsletter = true
                        company_profile_api().newsletter(setNewsletter, context) {}
                    } else {
                        setNewsletter = false
                        company_profile_api().newsletter(setNewsletter, context) {}
                    }
                }
                discover.setOnClickListener {
                    if (discover.isChecked == true) {
                        company_profile_api().discoverable(context) {}
                    } else {
                        company_profile_api().undiscoverable(context) {}
                    }
                }
            }
        }

        btnDeactive.setOnClickListener {
            val sheet = ModalDeactivateAccount()
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }

        username.text = user?.username
        email.text = user?.email
        phone.text = user?.phone

//        btnConnectGoogle.setOnClickListener {
//            signIn()
//        }
        return view
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
                                Log.d("SocialMedia", it.toString())
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