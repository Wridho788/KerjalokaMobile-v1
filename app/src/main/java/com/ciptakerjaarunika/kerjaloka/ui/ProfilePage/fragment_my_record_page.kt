package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.EduAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.ExpAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.LanguageAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.RecordAdapter

class fragment_my_record_page : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterRec: RecyclerView.Adapter<RecordAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_record_page, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ProfileAPI().JobseekerGetRecord(context){ res->
            if(res == null || res.data.isEmpty()){
                view.findViewById<TextView>(R.id.no_data_txt).visibility = VISIBLE
            }
            else{
                val recyclerViewLang = view.findViewById<RecyclerView>(R.id.recycleRec)
                recyclerViewLang.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapterRec = RecordAdapter(res?.data)
                }
            }
        }
    }


    companion object {
    }
}