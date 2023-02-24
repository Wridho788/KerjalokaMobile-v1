package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Test.Listener.TestDetailListener
import com.google.gson.Gson

class view_mytest_list : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterTest: RecyclerView.Adapter<mytest_adapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_mytest_list, container, false)

        company_profile_api().MyTest(context) {
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.layoutManager = layoutManager
            adapterTest = it?.let { it1 -> assignAdapter(it1.data) }
            recyclerView.adapter = adapterTest
        }

        return view
    }

    internal fun assignAdapter(list: List<Test>): mytest_adapter {
        return mytest_adapter(list, object : TestDetailListener {
            override fun detail(testDetail: Test) {
                replaceFragment(testDetail)
            }
        })
    }

    private fun replaceFragment(test: Test?) {
        val testDetailFragment = TestDetail()
        val mBundle = Bundle()
        val testData = Gson().toJson(test)
        mBundle.putString(TestDetail.EXTRA_DETAIL_TEST, testData)
        testDetailFragment.arguments = mBundle
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction()?.apply {
            replace(
                R.id.fragment_container,
                testDetailFragment,
                TestDetail::class.java.simpleName
            )
            addToBackStack(null)
            commit()

        }
    }

}