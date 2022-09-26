package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.BottomSheet

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.anychart.core.annotations.Line
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.JobShortQuestion
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.enum.QuestinoType
import com.ciptakerjaarunika.kerjaloka.model.Job.ApplicationTests
import com.ciptakerjaarunika.kerjaloka.model.Job.ApplyJobRequest
import com.ciptakerjaarunika.kerjaloka.model.Job.JobShortAnswer
import com.ciptakerjaarunika.kerjaloka.model.Job.JobShortAnswerChoices
import com.ciptakerjaarunika.kerjaloka.model.Test.JobShortQuestions
import com.ciptakerjaarunika.kerjaloka.model.Test.ShortQuestions
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobDetailModel
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobDetailScreen.IJobDetail
import com.google.android.material.button.MaterialButton

class ApplyJob(val job : rJobDetailModel?, var jobShortQuestions: List<JobShortQuestions>, val iJobDetail: IJobDetail) : SuperBottomSheetFragment() {
    private var questionNo : Int = 0;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_apply_job_modal, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val action_button = view?.findViewById<MaterialButton>(R.id.action_button)
        val reasonApply = view?.findViewById<EditText>(R.id.reason_apply_txt)
        var thisModal = this



        if(job != null){
            var tests : List<ApplicationTests> = listOf()
            var shortAnswer : List<JobShortAnswer> = listOf()

            job.jobTests.forEach { test->
                tests += ApplicationTests(
                    null,
                    null,
                    test.testNo!!,
                    test.testPeriod,
                    null,
                    null
                )
            }
            if(jobShortQuestions?.any() == true){
                action_button?.text = "Berikutnya"

                action_button?.setOnClickListener {
                    if(questionNo >= jobShortQuestions.size){
                        jobShortQuestions.forEach { short ->
                            var choices :List<JobShortAnswerChoices> = listOf()
                            var answer : String = ""
                            short.choice.forEach {
                                choices += JobShortAnswerChoices(
                                    short.shortQuestionNo,
                                    it.isSelected,
                                    it.choice
                                )
                                if(it.isSelected){
                                    answer += if(answer == "") it.choice else ",${it.choice}"
                                }
                            }

                            shortAnswer += JobShortAnswer(
                                short.shortQuestionNo,
                                answer,
                                choices
                            )
                        }

                        JobAPI().ApplyJob(job.jobNo,
                            ApplyJobRequest(
                                message = reasonApply?.text.toString(),
                                tests = tests,
                                shortQuestionAnswer = shortAnswer), context
                        ){
                            if (it != null) {
                                if (it?.code == "210") {
                                    thisModal
                                    iJobDetail.RefreshData()
                                } else {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(context, "Terjadi kesalahan yang tidak diketahui", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else {
                        changeQuestion()
                    }
                }
            }
            else{
                action_button?.setOnClickListener {
//                job.jobShortQuestion.forEach { test->
//                }
                    JobAPI().ApplyJob(job.jobNo,
                    ApplyJobRequest(
                        message = reasonApply?.text.toString(),
                        tests = tests,
                        listOf()), context
                    ){
                        if (it != null) {
                            if (it?.code == "210") {
                                thisModal.dismiss()
                                iJobDetail.RefreshData()
                            } else {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(context, "Terjadi kesalahan yang tidak diketahui", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    fun changeQuestion(){

        val action_button = view?.findViewById<MaterialButton>(R.id.action_button)

        view?.findViewById<LinearLayout>(R.id.reason_container)?.visibility = GONE
        view?.findViewById<LinearLayout>(R.id.short_question_container)?.visibility = VISIBLE
        view?.findViewById<EditText>(R.id.essay_answer)?.visibility = GONE
        view?.findViewById<RecyclerView>(R.id.recycle_choice_answer)?.visibility = GONE

        questionNo += 1;
        if(questionNo >= jobShortQuestions!!.size){
            action_button?.text = "Lamar"
        }
        if(jobShortQuestions.any()){
            view?.findViewById<TextView>(R.id.quesion_no)?.text = "Pertanyaan ${questionNo}"

            val index = questionNo - 1

            val currentQuestion = jobShortQuestions[index]
            view?.findViewById<TextView>(R.id.choice_question)?.text = currentQuestion.shortQuestion

            if(currentQuestion.questionType == QuestinoType.Essay.value){
                view?.findViewById<EditText>(R.id.essay_answer)?.visibility = VISIBLE
            }
            else if(currentQuestion.questionType == QuestinoType.MultipleAnswer.value ||
                    currentQuestion.questionType == QuestinoType.MultipleChoice.value){
                val recycle = view?.findViewById<RecyclerView>(R.id.recycle_choice_answer)
                recycle?.visibility = VISIBLE
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = ChoiceAdapter(currentQuestion.choice, currentQuestion.questionType)
                }
            }
        }
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }
    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        return (displayMetrics.heightPixels * 0.8).toInt();
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true
    }
}