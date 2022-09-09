package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseLanguage
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseScore



class fragment_edit_edit_bahasa : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_bahasa, container, false)
        val lang=view.findViewById<TextView>(R.id.js_EditLang)
        val sLisan = view.findViewById<TextView>(R.id.edit_scorelisan)
        val sTulisan = view.findViewById<TextView>(R.id.edit_scoretulisan)

        lang.setOnClickListener {
            val sheet = ChooseLanguage()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        sLisan.setOnClickListener {
            val sheet = ChooseScore()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        sTulisan.setOnClickListener {
            val sheet = ChooseScore()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_edit_edit_bahasa.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_edit_edit_bahasa().apply {

            }
    }
}