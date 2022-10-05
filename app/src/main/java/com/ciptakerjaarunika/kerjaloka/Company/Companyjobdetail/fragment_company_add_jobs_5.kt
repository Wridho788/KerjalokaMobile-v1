package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.BottomSheetShortCategory
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.QuestionCategory_adapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.Question_adapter
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.short_question_api
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.short_question_search
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentCompanyAddJobs5Binding
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestion

class fragment_company_add_jobs_5(val iAddJob: iAddJob) : Fragment(), iUpdatePage5 {
    private var short_question_list: List<ShortQuestion>? = null
    private lateinit var binding: FragmentCompanyAddJobs5Binding
    var getShortQuestion: List<ShortQuestion>? = listOf()
    var getShortQuestionCategory: Long? = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyAddJobs5Binding.inflate(layoutInflater)
        val view = binding.root

        binding.btnKembaliCmpny.setOnClickListener {
            replaceFragment(fragment_company_add_jobs_4(this.iAddJob))
        }

        binding.compnyInsertKategoriPerpen.setOnClickListener {
            categoryModal()
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
                                adapter = Question_adapter(
                                    short_question_list!!,
                                    this@fragment_company_add_jobs_5
                                )
                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isEmpty()) {
                    short_question_api().getShortQuestion(context) {
                        if (it != null) {
                            short_question_list = it.data
                            binding.listShortQuestions.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = Question_adapter(
                                    short_question_list!!,
                                    this@fragment_company_add_jobs_5
                                )
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
                    adapter =
                        Question_adapter(short_question_list!!, this@fragment_company_add_jobs_5)
                }
//                if (getShortQuestionCategory != null && !getShortQuestionCategory.toString()
//                        .isNullOrBlank() && !getShortQuestionCategory.toString().isNullOrEmpty()
//                ){

//                }
            }
        }
        if (getShortQuestionCategory != null && !getShortQuestionCategory.toString()
                .isNullOrBlank() && !getShortQuestionCategory.toString().isNullOrEmpty()
        ) {
            short_question_api().getShortQuestion(context) {
                if (it != null) {
                    short_question_list = it.data
                    val short_filter =
                        short_question_list!!.filter { it.shortQuestionCategoryNo == getShortQuestionCategory }
                    Log.d("short_filter", short_filter.toString())
                    binding.listShortQuestionsCategory.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = QuestionCategory_adapter(
                            short_filter,
                            this@fragment_company_add_jobs_5,
                            getShortQuestionCategory!!
                        )
                    }

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

    fun categoryModal() {
        val sheet = BottomSheetShortCategory(this)
        activity?.let { it -> sheet.show(it.supportFragmentManager, "category") }
    }

    override fun updateShortQuestion(shortQuestion: List<ShortQuestion>) {
        getShortQuestion = shortQuestion
    }

    override fun updateCategory(shortQuestionCategoryNo: Long) {
        getShortQuestionCategory = shortQuestionCategoryNo

    }
}

interface iUpdatePage5 {
    fun updateShortQuestion(shortQuestion: List<ShortQuestion>)
    fun updateCategory(shortQuestionCategoryNo: Long)
}