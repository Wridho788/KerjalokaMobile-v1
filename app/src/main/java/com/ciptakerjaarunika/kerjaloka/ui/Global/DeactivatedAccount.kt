package com.ciptakerjaarunika.kerjaloka.ui.Global

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

        binding.btnLogout.setOnClickListener {
            ProfileAPI().Logout(SessionManager(baseContext).device_token, baseContext) {
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

        binding.haloKerjaloka.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("mailto:halo@kerjaloka.com"));
            intent.putExtra(Intent.EXTRA_EMAIL,   "halo@kerjaloka.com");
            startActivity(intent);
        }

        binding.activated.setOnClickListener{
            ProfileAPI().GetReactivateAccount(baseContext) {
                if (it != null) {
                    Toast.makeText(baseContext, "Reactivate Account", Toast.LENGTH_SHORT).show()
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.deleteAcc.setOnClickListener {
            val sheet = GlobalDeleteModal()
            sheet.show(supportFragmentManager, "terminate")
//            baseContext?.let { it1 ->
//                sheet.show(
//                    it1.parentfragment,
//                    "ApplyJob"
//                )
//            }
        }

    }

}