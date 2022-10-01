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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ProfilePage
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.ciptakerjaarunika.kerjaloka.model.User.User
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.google.android.material.button.MaterialButton


class AkunPage() : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        btn_login?.setOnClickListener{

            val email = itemView.findViewById<EditText>(R.id.txt_email).text.toString()
            val password = itemView.findViewById<EditText>(R.id.txt_password).text.toString()
            val loginRequest = LoginRequest(email = email, password=password)
            AUTHAPI().Login(context, loginRequest){
                Log.d("Login Response", it.toString());
                if(it != null && it.code == 252){

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

                    val fragmentTransaction = parentFragmentManager.beginTransaction()
                    fragmentTransaction.replace(id, AkunPage())
                    fragmentTransaction.commit()
                }
                else{
                    SessionManager(context).user = null
                }
            }
        }

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            return inflater.inflate(R.layout.activity_login, container, false)
    }
}