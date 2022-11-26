package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchMoreJob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.api.Search_Api
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentSearchMoreJobBinding
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.JobDetailFragment
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchMoreJob.Adapter.SearchMoreJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.jobList

class SearchMoreJobFragment(var query: String) : Fragment(), OnFragmentClickListener {

    private lateinit var binding: FragmentSearchMoreJobBinding
    private var isLoading: Boolean = true
    private var list: List<jobList>? = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMoreJobBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        if (query.isNotEmpty() == true) {
            Search_Api().getGeneralSearchAsync(context, query) {
                binding.spinner.visibility = View.GONE
                if (it != null) {
                    isLoading = false
                    list = it.data.jobList
                    binding.rvMoreJob.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = SearchMoreJobAdapter(list!!, context, this@SearchMoreJobFragment)
                    }

                }
            }

        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(id, fragment)
        ft.addToBackStack("")
        ft.commit()
    }

    override fun onJobDetailPage(companyNo: Long, jobNo: Long) {
        replaceFragment(JobDetailFragment(JobNo = jobNo, CompanyNo = companyNo))
    }

    override fun BookmarkJob(jobNo: Long, Index: Int) {
        var bookmark = !list!![Index].bookmarked
        JobAPI().BookmarkJob(jobNo, bookmark, context) {
            if (it != null) {
                if (it.code == 210) {
                    binding.rvMoreJob.adapter?.notifyDataSetChanged()
                    Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, it.Message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}
interface OnFragmentClickListener {
    fun onJobDetailPage(companyNo: Long, jobNo: Long)
    fun BookmarkJob(jobNo: Long, Index: Int)
}