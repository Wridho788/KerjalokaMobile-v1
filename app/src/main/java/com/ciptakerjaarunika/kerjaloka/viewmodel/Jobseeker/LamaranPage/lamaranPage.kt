package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.LamaranPage


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
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.model.Job.ApplicationData
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.LoginPage.Login
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace

class LamaranPage : Fragment(), LamaranCellClickListener {

    @AddTrace(name="onLamaranPageTrace", enabled = true)
    class ItemCache{
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun lamaranPageTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("lamaran_page_trace")
        myTrace.start()
        val item = cache.fetch("item")
        if (item != null) {
            myTrace.incrementMetric("item_cache_hit", 1)
        } else {
            myTrace.incrementMetric("item_cache_miss", 1)
        }
        myTrace.stop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lamaranPageTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lamaran, container, false)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
       val toolbar = itemView.findViewById<MaterialToolbar>(R.id.mainToolbar) as MaterialToolbar
        toolbar.setTitle("Lamaran Saya")


        if(SessionManager(context).user == null){
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(id, Login(this, "lamaran"))
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
                        if(!it?.data.isNullOrEmpty()) {
                            layoutManager = LinearLayoutManager(activity)
                            adapter = Application(it.data, context, this@LamaranPage)
                        }
                    }
                }
            }
        }

    }
    override fun onCellClickListener(jobNo: Long, companyNo: Long, applicationData : ApplicationData?) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(id, viewJobDetail(jobNo, companyNo, applicationData), "JobDetailFragment")
        ft.addToBackStack("Lamaran Page")
        ft.commit()
    }
}

interface LamaranCellClickListener {
    fun onCellClickListener(jobNo : Long, companyNo : Long, applicationData: ApplicationData?)
}