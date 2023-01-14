package com.ciptakerjaarunika.kerjaloka.viewmodel.Components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityDeactivatedAccountBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager

class DeactivatedAccount : AppCompatActivity() {
    private lateinit var binding: ActivityDeactivatedAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeactivatedAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var context = baseContext

        binding.spinnerLogin.visibility = View.VISIBLE

        if (SessionManager(context).user != null) {
            binding.spinnerLogin.visibility = View.GONE
            binding.btnLogout.setOnClickListener {
                ProfileAPI().Logout(SessionManager(context).device_token, context) {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            binding.haloKerjaloka.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:halo@kerjaloka.com")
                intent.putExtra(Intent.EXTRA_EMAIL, "halo@kerjaloka.com")
                startActivity(intent)
            }

            binding.activated.setOnClickListener {
                binding.spinnerLogin.visibility = View.VISIBLE
                ProfileAPI().GetReactivateAccount(context) {
                    if (it != null) {
                        binding.spinnerLogin.visibility = View.GONE
                        Toast.makeText(context, "Reactivate Account", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            binding.deleteAcc.setOnClickListener {
                val sheet = GlobalDeleteModal()
                sheet.show(supportFragmentManager, "terminate")
            }
        } else {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

}