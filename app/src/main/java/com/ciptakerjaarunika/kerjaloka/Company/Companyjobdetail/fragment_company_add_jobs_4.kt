package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.TestAdapter
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.TestList
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs4Binding
import com.ciptakerjaarunika.kerjaloka.model.Data.TestJob

class fragment_company_add_jobs_4(val iAddJob: iAddJob) : Fragment(), iChooseTest {
    private lateinit var binding: FragmentCompanyAddJobs4Binding
    private var list: List<TestJob>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyAddJobs4Binding.inflate(layoutInflater)
        val view = binding.root
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnKembaliCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_3(this.iAddJob))
        }
        binding.btnSelanjutnyaCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_5(this.iAddJob))
        }


        TestList().GetTest(context){
            if(it != null){
                list = it.data
                binding.listTest.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TestAdapter(list,this@fragment_company_add_jobs_4)
                }
            }
//            res -> list
//            Log.d("res", res.toString())

        }

        return view
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, fragment)
        fragmentTransaction?.commit()
    }

    override fun updateTest(testNo: Long) {
        Log.d("test no", testNo.toString())
    }
}

interface iChooseTest{
    fun updateTest(testNo: Long)
}