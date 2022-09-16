package com.ciptakerjaarunika.kerjaloka.Company.Test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ciptakerjaarunika.kerjaloka.Company.Profile.Adapter.CompReviewAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Profile.company
import com.ciptakerjaarunika.kerjaloka.Company.Profile.user
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.CellClickListener
import com.google.android.material.appbar.MaterialToolbar

class view_mytest_list : Fragment(),CellClickListener {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterTest: RecyclerView.Adapter<mytest_adapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_mytest_list, container, false)

        val testList = ArrayList<Test>()
        val quesList = ArrayList<Question>()

        val q1 = Question(
            questionNo = 363,
            question = "Jawaban benar hanya 1 dan 3",
            type = 3,
            maxScore = 20,
            subQuestion = 0
        )
        val q2 = Question(
            questionNo = 363,
            question = "OHHH Let it Be",
            type = 3,
            maxScore = 20,
            subQuestion = 0
        )
        val q3 = Question(
            questionNo = 363,
            question = "Let It Be",
            type = 3,
            maxScore = 20,
            subQuestion = 0
        )
        quesList.add(q1)
        quesList.add(q2)
        quesList.add(q3)

        val test1 = Test(
            testNo = 70,
            testName = "Test Jawaban Ganda",
            testDuration = 12,
            testPeriod = 3,
            maxScore = 230,
            testEnabled = true,
            testHint = "",
            isPublic = false,
            isSpecial = false,
            createdBy = "TESTING",
            createdOn = "2022-05-21T12:02:05",
            questions = quesList,
            isTakedown = false,
            isOwn = true,
            testLink = null,
            testMarketNo = null,
            price = null
        )
        val test2 = Test(
            testNo = 70,
            testName = "Test Jawaban Ganda",
            testDuration = 12,
            testPeriod = 3,
            maxScore = 230,
            testEnabled = true,
            testHint = "",
            isPublic = false,
            isSpecial = false,
            createdBy = "TESTING TESTING TESTING TESTING",
            createdOn = "2022-05-21T12:02:05",
            questions = quesList,
            isTakedown = false,
            isOwn = true,
            testLink = null,
            testMarketNo = null,
            price = null
        )
        testList.add(test1)
        testList.add(test2)
        testList.add(test1)
        testList.add(test1)
        testList.add(test1)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView;
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        adapterTest = mytest_adapter(testList)
        recyclerView.adapter = adapterTest

        return view
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
    }
    override fun onCellClickListener() {
//        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
//        ft.replace(id, JobDetailFragment(), "JobDetailFragment")
//        ft.addToBackStack(null)
//        ft.commit()
    }

    companion object {
    }
}