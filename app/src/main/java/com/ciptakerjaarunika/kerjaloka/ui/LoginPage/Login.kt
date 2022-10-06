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
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.ciptakerjaarunika.kerjaloka.model.User.User
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.reactnativegooglesignin.RNGoogleSigninModule.RC_SIGN_IN


class Login(val Goto: Fragment, val nameFragment: String) : Fragment() {

    companion object {
        var googleSignInClient: GoogleSignInClient? = null
        val REQUEST_CODE_GOOGLE_SIGN_IN = 0
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("863470789028-pmlnd7u7bifuj5ep8cvdp70eq3469nmb.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = context?.let {
            GoogleSignIn.getClient(
                it, gso
            )
        }

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
            val signInIntent: Intent = googleSignInClient!!.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d("data",data.toString())
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            Log.d("signInResult:failed code=" + e.statusCode, e.toString())
//            updateUI(null)
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