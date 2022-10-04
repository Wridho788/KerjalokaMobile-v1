package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.Question_adapter
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.short_question_api
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.short_question_search
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs5Binding
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestion

class fragment_company_add_jobs_5(val iAddJob: iAddJob) : Fragment() {
    private var short_question_list: List<ShortQuestion>? = null
    private lateinit var binding: FragmentCompanyAddJobs5Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyAddJobs5Binding.inflate(layoutInflater)
        val view = binding.root

        binding.btnKembaliCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_4(this.iAddJob))
        }

        binding.editSearchShortQuestion.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    short_question_search().getSearchShortQuestion(context, query) {
                        Log.d("response search", it.toString())
                        if (it != null) {
                            short_question_list = it.data

                            binding.listShortQuestions.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = Question_adapter(short_question_list!!)
                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true) {
                    short_question_search().getSearchShortQuestion(context, newText) {
                        Log.d("response search", it.toString())
                        if (it != null) {
                            short_question_list = it.data

                            binding.listShortQuestions.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = Question_adapter(short_question_list!!)
                            }
                        }
                    }
                }
                return true
            }
        })

        short_question_api().getShortQuestion(context) {
            if (it != null) {
                short_question_list = it.data
                binding.listShortQuestions.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = Question_adapter(short_question_list!!)
                }
            }
        }



        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(id, fragment)
        fragmentTransaction?.commit()
    }

}