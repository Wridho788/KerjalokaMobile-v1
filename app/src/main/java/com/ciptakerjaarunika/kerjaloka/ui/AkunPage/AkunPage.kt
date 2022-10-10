package com.ciptakerjaarunika.kerjaloka.ui.AkunPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ProfilePage
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.api.AUTHGOOGLEAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.User.GoogleLoginRequest
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.LoginPage.Login
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*


class AkunPage() : Fragment() {

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
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        if(SessionManager(context).user != null){
            if(SessionManager(context).user?.roleNo == 4) {
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id,profilepage(0),"ProfileFragment")
                ft.commit()
            }
            else{
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id, ProfilePage(),"ProfileFragment")
                ft.commit()
            }
        }

        val btn_login = itemView.findViewById<MaterialButton>(R.id.btnLogin)

        val btn_login_google = itemView.findViewById<MaterialButton>(R.id.btn_LoginGoogle)
        btn_login_google.setOnClickListener {
            signIn()
        }

        btn_login?.setOnClickListener{

            val email = itemView.findViewById<EditText>(R.id.txt_email).text.toString()
            val password = itemView.findViewById<EditText>(R.id.txt_password).text.toString()

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    val token = task.result
                    SessionManager(context).device_token = token
            })


            val loginRequest = LoginRequest(email = email, password=password, deviceToken = SessionManager(context).device_token.toString())
            AUTHAPI().Login(context, loginRequest) {
                Log.d("Login Response", it.toString());

                if (it != null) {
                    if (it.code == "252") {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                        val activity = activity as MainActivity
                        SessionManager(context).access_token = it.userToken
                        AUTHAPI().CheckLogin(context, activity) {
                            activity.replaceFragment(AkunPage())
                        }
                    } else {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        SessionManager(context).user = null
                    }
                    /*if(it != null && it.code == "252"){

                    SessionManager(context).access_token = it.userToken

                    var user = User(
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
                        email =  email,
                        emailHasVerified = null,
                        isDeleted = null,
                        isDiscoverable = false,
                        isNewsletter = null,
                        lastChangeUsername = null,
                        username = "",
                        company = null,
                        companyAdditional = null,
                        jobseekerAdditional = null,
                        jobseekers = null,
                        phone = "",
                    );
                    SessionManager(context).user = user

                    val mainActivity = activity as MainActivity
                    AUTHAPI().CheckLogin(mainActivity,mainActivity) {
                        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                        ft.replace(id, AkunPage(), "Akun Page")
                        ft.commit()
                    }
                }
                else if (it != null) {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        SessionManager(context).user = null
                    }
                }*/
                }
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(
            context!!,
            gso
        )

        val register = itemView.findViewById<TextView>(R.id.register)
        register?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kerjaloka.com/register"))
            startActivity(intent)
        })

        val forgotPswd = itemView.findViewById<TextView>(R.id.forgotPswd)
        forgotPswd?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kerjaloka.com/recovery"))
            startActivity(intent)
        })

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
                Login.SavedPreference.setEmail(context!!, account.email.toString())
                Login.SavedPreference.setUsername(context!!, account.displayName.toString())

                val text = "${account.idToken}${config().authKey}${4}"
                val crypt = MessageDigest.getInstance("MD5");
                crypt.update(text.toByteArray());
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
                    GoogleLoginRequest(account.idToken.toString(), oneHourBack.toString(), hash, deviceToken =  SessionManager(context).device_token)
                AUTHGOOGLEAPI().GoogleLogin(context, googleRequest) {
                    Log.d("google login", it.toString())
                    if (it != null)
                        if (it.code == "252") {
                            SessionManager(context).access_token = it.userToken
                            val mainActivity = activity as MainActivity

                            AUTHAPI().CheckLogin(context, mainActivity) {
                                mainActivity.replaceFragment(AkunPage())
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
}