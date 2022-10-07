package com.ciptakerjaarunika.kerjaloka.ui.Global

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet.ApplyJob
import com.google.android.material.button.MaterialButton


class ReactivateAccount : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reactivate_account, container, false)

        val contact = view.findViewById<TextView>(R.id.halo_kerjaloka)
        val deleteAcc = view.findViewById<TextView>(R.id.delete_acc)
        val btn_Act = view.findViewById<MaterialButton>(R.id.activated)
        val btn_Logout = view.findViewById<MaterialButton>(R.id.btn_logout)

        contact.setOnClickListener({
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL,   "halo@kerjaloka.com");
            startActivity(intent);
        }
        )

        deleteAcc.setOnClickListener {
            val sheet = GlobalDeleteModal()
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "ApplyJob"
                )
            }
        }

        return view
    }

    companion object {

    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}
