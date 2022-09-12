package com.ciptakerjaarunika.kerjaloka.ui.LamaranPage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.LoginPage.Login
import com.google.android.material.appbar.MaterialToolbar
import com.microsoft.signalr.HubConnection


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LamaranPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class LamaranPage : Fragment(), LamaranCellClickListener {
    // TODO: Rename and change types of parameters

    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<Application.ViewHolder>? = null
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
        return inflater.inflate(R.layout.fragment_lamaran, container, false)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
       val toolbar = itemView.findViewById<MaterialToolbar>(R.id.mainToolbar) as MaterialToolbar
        toolbar.setTitle("Lamaran Saya")


        var Context = this;
        if(SessionManager(context).user == null){
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(id, Login())
            fragmentTransaction.commit()
        }
        else {
            JobAPI().GetMyAPplications(context) {
                if (it != null) {
                    itemView.findViewById<LinearLayout>(R.id.spinnerLamaran).visibility = GONE

                    val recyclerView =
                        itemView.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView;

                    recyclerView.visibility = VISIBLE
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = Application(it.data, context, Context)
                    }
                }
            }
        }

    }
    override fun onCellClickListener() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, viewJobDetail(), "JobDetailFragment")
        ft.addToBackStack("Lamaran Page")
        ft.commit()
    }
}

interface LamaranCellClickListener {
    fun onCellClickListener()
}