package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentMyRecordPageBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.RecordAdapter
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace

class fragment_my_record_page : Fragment() {
    private lateinit var binding : FragmentMyRecordPageBinding

    @AddTrace(name = "onRecordPageTrace", enabled = true)
    class ItemCache {
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun myRecordTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("my_record_page_trace")
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
        myRecordTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyRecordPageBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ProfileAPI().JobseekerGetRecord(context){ res->
            if(res != null) {
                binding.spinner.visibility = GONE
                binding.contentContainer.visibility = VISIBLE
                if (res.data.isEmpty()) {
                    binding.noDataTxt.visibility = VISIBLE
                } else if (res != null) {
                    binding.recycleRec.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = activity?.let { RecordAdapter(res.data, it) }
                    }
                }
            }
        }
    }
}