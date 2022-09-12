package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditMarital
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditReligion
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditResident

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditAddInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditAddInfo : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_edit_add_info, container, false)
        val marital = view.findViewById<TextView>(R.id.js_EditMarital)
        val religi = view.findViewById<TextView>(R.id.js_EditReligi)
        val resident = view.findViewById<TextView>(R.id.jsEditResident)

        marital.setOnClickListener {
            val sheet = EditMarital()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        religi.setOnClickListener {
            val sheet = EditReligion()
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        resident.setOnClickListener {
            val sheet = EditResident()
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
         * @return A new instance of fragment EditAddInfo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditAddInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}