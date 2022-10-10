package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.BottomSheetShortCategory
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.adapter.ShortQuestion_adapter
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iAddidiontalInfoPage
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyAddJob.short_question_api
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentShortQuestionPageBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestion
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestionCategory
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.CompanyJobDetail

class ShortQuestionPage(val data : CompanyJobDetail,val iAddidiontalInfoPage: iAddidiontalInfoPage) : Fragment(), iUpdatePage5 {
    private var short_question_list: List<ShortQuestion>? = null
    private lateinit var binding: FragmentShortQuestionPageBinding
    var categoryNo : Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShortQuestionPageBinding.inflate(layoutInflater)
        val view = binding.root

        binding.selectCategory.setOnClickListener {
            categoryModal()
        }
        short_question_api().getShortQuestion(context) {
            if (it != null) {
                short_question_list = it.data
                binding.listShortQuestions.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter =
                        ShortQuestion_adapter(
                            data.jobShortQuestion,
                            short_question_list!!, iAddidiontalInfoPage)
                }

                binding.search.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun afterTextChanged(p0: Editable?) {
                        if(binding.search.text.toString().isNullOrEmpty() ||binding.search.text.toString().isNullOrBlank()){
                            var temp = it.data.filter { item ->
                                categoryNo == null || item.shortQuestionCategoryNo == categoryNo
                            }
                            binding.listShortQuestions.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = ShortQuestion_adapter(
                                    data.jobShortQuestion,
                                    temp!!,
                                    iAddidiontalInfoPage
                                )
                            }
                            binding.listShortQuestions.adapter?.notifyDataSetChanged()
                        }
                        else {
                            var temp = it.data.filter { item ->
                                (categoryNo == null || item.shortQuestionCategoryNo == categoryNo)
                                        && (binding.search.text.toString().isNullOrBlank() || binding.search.text.toString().isNullOrEmpty()||
                                        item.shortQuestion.toLowerCase().contains(binding.search.text.toString().toLowerCase()))
                            }
                            binding.listShortQuestions.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = ShortQuestion_adapter(
                                    data.jobShortQuestion,
                                    temp!!,
                                    iAddidiontalInfoPage
                                )
                            }
                            binding.listShortQuestions.adapter?.notifyDataSetChanged()
                        }
                    }
                })
            }
        }
        return view
    }

    fun categoryModal() {
        val sheet = BottomSheetShortCategory(this)
        activity?.let { it -> sheet.show(it.supportFragmentManager, "category") }
    }

    override fun updateCategory(category: ShortQuestionCategory?) {
        categoryNo = category?.shortQuestionCategoryNo
        if(category != null){
            binding.categoryTxt.text = category.categoryName
            binding.actionCategory.setImageResource(R.drawable.ic_close)
            binding.actionCategory.setOnClickListener {
                updateCategory(null)
            }
        }
        else{
            binding.categoryTxt.text = "Pilih Kategori"
            binding.actionCategory.setImageResource(R.drawable.ic_chevron_down)
            binding.actionCategory.setOnClickListener {
                categoryModal()
            }
        }
        var temp= short_question_list?.filter { item->
            (categoryNo == null || item.shortQuestionCategoryNo == categoryNo)
                && (binding.search.text.toString().isNullOrBlank() || binding.search.text.toString().isNullOrEmpty()||
                    item.shortQuestion.toLowerCase().contains(binding.search.text.toString().toLowerCase()))
        }
        binding.listShortQuestions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ShortQuestion_adapter( data.jobShortQuestion, temp!!, iAddidiontalInfoPage )
        }
        binding.listShortQuestions.adapter?.notifyDataSetChanged()
    }
}

interface iUpdatePage5 {
    fun updateCategory(category: ShortQuestionCategory?)
}