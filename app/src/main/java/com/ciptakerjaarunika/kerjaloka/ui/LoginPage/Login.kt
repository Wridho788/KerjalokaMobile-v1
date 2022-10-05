package com.ciptakerjaarunika.kerjaloka.ui.LoginPage

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
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.ciptakerjaarunika.kerjaloka.model.User.User
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton


class Login(val Goto: Fragment, val nameFragment: String) : Fragment() {

    companion object {
        var googleSignInClient: GoogleSignInClient? = null
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient= context?.let {
            GoogleSignIn.getClient(
                it,gso)
        };
//        val googleSignInOptions = GoogleSignInOptions.Builder(
//            GoogleSignInOptions.DEFAULT_SIGN_IN
//        ).requestIdToken("438431947620-ecpi41uk3dhhf4mv8g8q993k3vs49ltm.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
        Log.d("Klik", "Start")

        val btn_login = itemView.findViewById<MaterialButton>(R.id.btnLogin)
        val btn_login_google = itemView.findViewById<MaterialButton>(R.id.btn_LoginGoogle)

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

        btn_login_google.setOnClickListener {
//            signInGoogle()
            Toast.makeText(activity, "Sign in Google", Toast.LENGTH_SHORT).show()
            val intent = googleSignInClient!!.signInIntent
            startActivityForResult(intent, 100)
        }

        val register = itemView.findViewById<TextView>(R.id.register)
        register.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kerjaloka.com/register"))
            startActivity(intent)
        }

        val forgotPswd = itemView.findViewById<TextView>(R.id.forgotPswd)
        forgotPswd.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kerjaloka.com/recovery"))
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val temp = data?.data
            Log.d("temp", temp.toString())
            val credential: SignInCredential =
                Identity.getSignInClient(requireActivity()).getSignInCredentialFromIntent(data)
//            Log.d("TOken", credential.toString())
        // Signed in successfully - show authenticated UI
            // Signed in successfully - show authenticated UI
//
//
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            task.addOnSuccessListener { res ->
//                Log.d("TOken", res.toString())
//            }
//            task.addOnCompleteListener{res ->
//                Log.d("TOken", res.toString())
//            }
//            Log.d("TOken", task.result.toString())
//            // check condition
//            task.onSuccessTask { googleRes ->
//                return@onSuccessTask
//            }
//            {
//                // When google sign in successful
//                // Initialize string
//                val s = "Google sign in successful"
//                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
//                // Initialize sign in account
//                try {
//                    // Initialize sign in account
//                    val googleSignInAccount = signInAccountTask
//                        .getResult(ApiException::class.java)
//                    if(googleSignInAccount!=null)
//                    {
//                        Log.d("googleSignInAccount", googleSignInAccount.toString())
//
//                    }
//                } catch (e: ApiException) {
//                    e.printStackTrace ()
//
//                }
//            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_login, container, false)
    }

//    private fun signInGoogle() {
//        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }

}