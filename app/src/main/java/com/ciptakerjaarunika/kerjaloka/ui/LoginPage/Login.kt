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
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.enum.Role
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.ciptakerjaarunika.kerjaloka.model.User.User
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.CompanyDashboard
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranPage
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ListApplicant.CompanyListApplicantFragment
import com.google.android.material.button.MaterialButton


class Login(val Goto :Fragment, val nameFragment: String) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        Log.d("Klik", "Start")

        val btn_login = itemView.findViewById<MaterialButton>(R.id.btnLogin)

            btn_login.setOnClickListener{
                val email = view?.findViewById<EditText>(R.id.txt_email)?.text.toString()
                val password = view?.findViewById<EditText>(R.id.txt_password)?.text.toString()
                if(!email.isNullOrEmpty() && !email.isNullOrBlank() && !password.isNullOrEmpty() && !password.isNullOrBlank()) {

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
                                email =  email,
                                emailHasVerified = null,
                                isDeleted = null,
                                isNewsletter = null,
                                isDiscoverable = false,
                                lastChangeUsername = null,
                                username = "",
                                company = null,
                                companyAdditional = null,
                                jobseekerAdditional = null,
                                jobseekers = null,
                                phone = "",
                            );

                            SessionManager(context).user = User

                            AUTHAPI().CheckLogin(context) {
                                val mainActivity = activity as MainActivity
                                mainActivity.replaceFragment(Goto, nameFragment)
                            }
                        }
                    }
                }
        }

        val register = itemView.findViewById<TextView>(R.id.register)
        register.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kerjaloka.com/register"))
            startActivity(intent)
        })

        val forgotPswd = itemView.findViewById<TextView>(R.id.forgotPswd)
        forgotPswd.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kerjaloka.com/recovery"))
            startActivity(intent)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_login, container, false)
    }
}