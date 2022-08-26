package com.ciptakerjaarunika.kerjaloka.ui.LoginPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.AUTHAPI
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class Login : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        Log.d("Klik", "Start")
        val email = itemView.findViewById<TextInputEditText>(R.id.txt_email).text.toString()
        val password = itemView.findViewById<TextInputEditText>(R.id.txt_password).text.toString()
        val btn_login = itemView.findViewById<MaterialButton>(R.id.btnLogin)
        btn_login.setOnClickListener(View.OnClickListener {
            Log.d("Klik", "Clicked")
            AUTHAPI().Login(context,LoginRequest(email,password)){
                if(it != null){
                    Log.d("Login Response", it.toString());
                }
            }
        })

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