package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.ManageJobPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.adapter.TestAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.iAddidiontalInfoPage
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.TestList
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentTestJobPageBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.TestJob
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.CompanyJobDetail

class TestPage(val data: CompanyJobDetail, val updateData : iAddidiontalInfoPage) : Fragment() {
    private lateinit var binding: FragmentTestJobPageBinding
    private var list: List<TestJob>? = null
    var getTestJob: List<TestJob>? = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestJobPageBinding.inflate(layoutInflater)
        val view = binding.root
        TestList().GetTest(context) {
            if (it != null) {
                list = it.data
                binding.listTest.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TestAdapter(data.jobTest, list, updateData)
                }
            }
        }

        return view
    }
}

