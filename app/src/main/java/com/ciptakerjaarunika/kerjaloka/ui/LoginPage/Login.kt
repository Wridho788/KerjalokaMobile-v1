package com.ciptakerjaarunika.kerjaloka.ui.LoginPage

import android.content.Intent
import android.content.IntentSender.SendIntentException
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
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.ciptakerjaarunika.kerjaloka.model.User.User
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton


class Login(val Goto: Fragment, val nameFragment: String) : Fragment() {

    companion object {
         var mGoogleSignInClient: GoogleSignInClient? = null
        val REQUEST_CODE_GOOGLE_SIGN_IN = 0
        private val RC_SIGN_IN = 1
    }

//    override fun onStart() {
//        super.onStart()
//        val account = GoogleSignIn.getLastSignedInAccount(activity)
//        Log.d("account", account.idToken.toString())
//        if(account!=null) {
//            val mainActivity = activity as MainActivity
//            mainActivity.replaceFragment(Goto, nameFragment)
//        }
//    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(context!!, gso)

        val btn_login_google = itemView.findViewById<MaterialButton>(R.id.btn_LoginGoogle)

        btn_login_google.setOnClickListener {
            signIn()

        }

        val btn_login = itemView.findViewById<MaterialButton>(R.id.btnLogin)

        btn_login.setOnClickListener {
            val email = view?.findViewById<EditText>(R.id.txt_email)?.text.toString()
            val password = view?.findViewById<EditText>(R.id.txt_password)?.text.toString()
            if (!email.isNullOrEmpty() && !email.isNullOrBlank() && !password.isNullOrEmpty() && !password.isNullOrBlank()) {

                AUTHAPI().Login(context, LoginRequest(email, password)) {
                    if (it != null) {

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

                        AUTHAPI().CheckLogin(context) {
                            val mainActivity = activity as MainActivity
                            mainActivity.replaceFragment(Goto, nameFragment)
                        }
                    }
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
//        val intent = mGoogleSignInClient!!.signInIntent
//        startActivityForResult(intent, RC_SIGN_IN)
        val request = GetSignInIntentRequest.builder()
            .setServerClientId("953263165300-d93upe5e31bsfb1au1ns41c2ijpa2tb4.apps.googleusercontent.com")
            .build()

        Log.d("request", request.toString())

        Identity.getSignInClient(context!!)
            .getSignInIntent(request)
            .addOnSuccessListener { result ->
                try {
                    startIntentSenderForResult(
                        result.getIntentSender(),
                        REQUEST_CODE_GOOGLE_SIGN_IN,  /* fillInIntent= */
                        null,  /* flagsMask= */
                        0,  /* flagsValue= */
                        0,  /* extraFlags= */
                        0,  /* options= */
                        null
                    )
                } catch (e: SendIntentException) {
                    Log.d("Google Sign-in failed", e.toString())
                }
            }
            .addOnFailureListener { e -> Log.d( "Google Sign-in failed", e.toString()) }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d("onActivityResult", task.getResult().toString())

            try {
                val account :GoogleSignInAccount? = task.getResult(ApiException::class.java)
//                val intent = Intent(context, MainActivity::class.java)
//                startActivity(intent)

                Log.d("account", account!!.idToken.toString())


            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.e("TAG","signInResult:failed code=" + e.statusCode)
            }
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            Log.d("account signin",account.toString())
        } catch (e: ApiException) {
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show()
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