package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.ProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentMyRecordPageBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.RecordAdapter

class fragment_my_record_page : Fragment() {
    private lateinit var binding : FragmentMyRecordPageBinding

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