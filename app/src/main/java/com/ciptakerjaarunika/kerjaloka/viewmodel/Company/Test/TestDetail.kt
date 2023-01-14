package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test.Adapter.QuestionTestAdapter
import com.ciptakerjaarunika.kerjaloka.R
import com.google.gson.Gson

class TestDetail : Fragment() {

    var testData: Test? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterTest: RecyclerView.Adapter<QuestionTestAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtTestName = view.findViewById<TextView>(R.id.testName)
        val txtTestOwn = view.findViewById<TextView>(R.id.testown)
        val txtTestDuration = view.findViewById<TextView>(R.id.duration)
        val txtTestPeriod = view.findViewById<TextView>(R.id.period)
        if (arguments != null) {
            val descFromBundle = arguments?.getString(EXTRA_DETAIL_TEST)
            testData = Gson().fromJson(descFromBundle, Test::class.java)

            txtTestName.text = testData?.testName
            txtTestOwn.text = testData?.createdBy
            txtTestDuration.text = testData?.testDuration.toString()
            txtTestPeriod.text = testData?.testPeriod.toString()
            val recyclerView = view.findViewById<RecyclerView>(R.id.question_list) as RecyclerView;
            layoutManager = LinearLayoutManager(activity)
            recyclerView.layoutManager = layoutManager
            adapterTest = testData?.questions?.let { QuestionTestAdapter(it) }
            recyclerView.adapter = adapterTest
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test_detail, container, false)

        return view
    }

    companion object {
        var EXTRA_DETAIL_TEST = "extra_detailTes"
    }
}