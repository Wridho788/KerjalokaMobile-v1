package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EditExp_TypeJob
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [manage_cv_edit_experience_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class manage_cv_edit_experience_page : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manage_cv_edit_experience_page, container, false)
        val cType = view.findViewById<TextView>(R.id.pilih_tipe_pekerjaan)
        val cLoc = view.findViewById<TextView>(R.id.pilih_lokasi_perusahaan)
        val cStartM = view.findViewById<TextView>(R.id.pilih_bulan_mulai)
        val cEndM = view.findViewById<TextView>(R.id.pilih_bulan_berakhir)
        val cStartY = view.findViewById<TextView>(R.id.pilih_tahun_mulai)
        val cEndY = view.findViewById<TextView>(R.id.pilih_tahun_berakhir)

        cType.setOnClickListener {
            val sheet = EditExpTypeJob()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        cLoc.setOnClickListener {
            val sheet = EditExpCompLoc()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        cStartM.setOnClickListener {
            val sheet = ChooseMonth()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        cEndM.setOnClickListener {
            val sheet = ChooseMonth()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        cStartY.setOnClickListener {
            val sheet = ChooseYear()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        cEndY.setOnClickListener {
            val sheet = ChooseYear()
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
         * @return A new instance of fragment manage_cv_edit_experience_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            manage_cv_edit_experience_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }
}