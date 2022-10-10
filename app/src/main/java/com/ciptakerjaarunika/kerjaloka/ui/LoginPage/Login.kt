package com.ciptakerjaarunika.kerjaloka.ui.LoginPage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.enum.Role
import com.ciptakerjaarunika.kerjaloka.api.AUTHGOOGLEAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.User.GoogleLoginRequest
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.CompanyListApplicantFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.reactnativegooglesignin.RNGoogleSigninModule.RC_SIGN_IN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.text.Charsets.UTF_8


class Login(val Goto: Fragment, val nameFragment: String) : Fragment() {

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


    override fun onStart() {
        super.onStart()
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        // Configure Google Sign In inside onCreate mentod
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()// getting the value of gso inside the GoogleSigninClient
        mGoogleSignInClient = GoogleSignIn.getClient(
            context!!,
            gso
        )// initialize the firebaseAuth variable firebaseAuth= FirebaseAuth.getInstance()
        val btn_login_google = itemView.findViewById<MaterialButton>(R.id.btn_LoginGoogle)

        btn_login_google.setOnClickListener {
            signIn()
        }

        val btn_login = itemView.findViewById<MaterialButton>(R.id.btnLogin)

        btn_login.setOnClickListener {
            val email = view?.findViewById<EditText>(R.id.txt_email)?.text.toString()
            val password = view?.findViewById<EditText>(R.id.txt_password)?.text.toString()
            if (!email.isNullOrEmpty() && !email.isNullOrBlank() && !password.isNullOrEmpty() && !password.isNullOrBlank()) {
                if(SessionManager(context).device_token.isNullOrEmpty()){
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            return@OnCompleteListener
                        }
                        val token = task.result
                        SessionManager(context).device_token = token
                    })
                }


                AUTHAPI().Login(context, LoginRequest(email, password, SessionManager(context).device_token.toString())) {
                    if (it != null){
                        if (it.code == "252") {
                            SessionManager(context).access_token = it.userToken
                            val mainActivity = activity as MainActivity
                            AUTHAPI().CheckLogin(context, mainActivity) {
                                if(nameFragment != "lamaran" || SessionManager(context).user == null || SessionManager(context).user?.roleNo == Role.Jobseekers.value) {
                                    mainActivity.replaceFragment(Goto)
                                }
                                else if (nameFragment == "lamaran" && SessionManager(context).user?.roleNo == Role.Companies.value || SessionManager(context).user?.company != null
                                ) {
                                    mainActivity.replaceFragment(CompanyListApplicantFragment())
                                }
                            }
                        } else {
                            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                            SessionManager(context).user = null

                        }
                        }
                    /*if (it != null && it.code == "252") {

                        SessionManager(context).access_token = it.userToken
                        var User = User(
                            userNo = it.userNo,
                            userFullname = it.userFullname,
                            suspended = it.suspended,
                            roleNo = it.userRole,
                            photo = it.photo,
                            deactivated = it.deactivated,
                            dataComplete = it.dataComplete,
                            ownerStatus = it.ownerStatus == true,
                            authorized = it.ownerStatus == true,
                            notice = it.notice,
                            rolePrivileges = it.privilege,
                            email = email,
                            emailHasVerified = null,
                            isDeleted = null,
                            isNewsletter = null,
                            lastChangeUsername = null,
                            username = "",
                            company = null,
                            companyAdditional = null,
                            jobseekerAdditional = null,
                            jobseekers = null,
                            phone = "",
                            isDiscoverable = null
                        )

                        SessionManager(context).user = User
                        val mainActivity = activity as MainActivity
                        AUTHAPI().CheckLogin(context, mainActivity) {
                            if(nameFragment != "lamaran" || SessionManager(context).user == null || SessionManager(context).user?.roleNo == Role.Jobseekers.value){
                                    val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                                    ft.replace(id, Goto,"")
                                    ft.commit()
                            }
                            else if (nameFragment == "lamaran" && SessionManager(context).user?.roleNo == Role.Companies.value || SessionManager(context).user?.company != null
                            ) {
                                    val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                                    ft.replace(id, CompanyListApplicantFragment(),"")
                                    ft.commit()
                            }
                        }
                    } else if (it != null) {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        SessionManager(context).user = null
                    }*/
                }
            }
        }

        val register =
            itemView.findViewById<TextView>(com.ciptakerjaarunika.kerjaloka.R.id.register)
        register.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kerjaloka.com/register"))
            startActivity(intent)
        }

        val forgotPswd =
            itemView.findViewById<TextView>(com.ciptakerjaarunika.kerjaloka.R.id.forgotPswd)
        forgotPswd.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kerjaloka.com/recovery"))
            startActivity(intent)
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, Req_Code)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
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
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val currentDate = Date()
        val cal: Calendar = Calendar.getInstance()
        // remove next line if you're always using the current time.
        cal.setTime(currentDate)
        cal.add(Calendar.HOUR, +1)
        val oneHourBack: Date = cal.getTime()

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                SavedPreference.setEmail(context!!, account.email.toString())
                SavedPreference.setUsername(context!!, account.displayName.toString())

                val text = "${account.idToken}${config().authKey}${4}"
                val crypt = MessageDigest.getInstance("MD5");
                crypt.update(text.toByteArray());
                val hash = BigInteger(1, crypt.digest()).toString(16)
                fun md5(str: String): ByteArray =
                    MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
                Log.d("Crypt", hash)

                val googleRequest =
                    GoogleLoginRequest(account.idToken.toString(), oneHourBack.toString(), hash)
                AUTHGOOGLEAPI().GoogleLogin(context, googleRequest) {
                    Log.d("google login", it.toString())
                    if (it != null)
                        if (it.code == "252") {
                            SessionManager(context).access_token = it.userToken
                            val mainActivity = activity as MainActivity

                            AUTHAPI().CheckLogin(context, mainActivity) {
                                mainActivity.replaceFragment(Goto)
                            }
                        } else {
                            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                            SessionManager(context).user = null
                        }
                }
            }
//                val intent = Intent(context, MainActivity::class.java)
//                startActivity(intent)
//                finish()


        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    object SavedPreference {
        const val EMAIL = "email"
        const val USERNAME = "username"
        private fun getSharedPreference(ctx: Context?): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        private fun editor(context: Context, const: String, string: String) {
            getSharedPreference(
                context
            )?.edit()?.putString(const, string)?.apply()
        }

        fun getEmail(context: Context) = getSharedPreference(
            context
        )?.getString(EMAIL, "")

        fun setEmail(context: Context, email: String) {
            editor(
                context,
                EMAIL,
                email
            )
        }

        fun setUsername(context: Context, username: String) {
            editor(
                context,
                USERNAME,
                username
            )
        }

        fun getUsername(context: Context) = getSharedPreference(
            context
        )?.getString(USERNAME, "")

    }
}
