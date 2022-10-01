package com.ciptakerjaarunika.kerjaloka.Company.Package

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.fragment_company_job_active_page
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.myPackageAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.Company.Package.Model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Package.Model.Package
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.google.gson.Gson

class company_package_list : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<myPackageAdapter.myPackage>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_company_package_list, container, false)

        company_profile_api().CompanyGetPackageData(context){

            val recyclerView = view.findViewById<RecyclerView>(R.id.myPackageRecycler)
            layoutManager = LinearLayoutManager(activity)
            recyclerView.layoutManager = layoutManager

            adapter = it?.data?.let { it1 -> assignAdapter(it1) }
            recyclerView.adapter = adapter
        }


        return view
    }

    internal fun assignAdapter(list: List<Data>): myPackageAdapter {
        return myPackageAdapter(requireContext(), list, object : ShowModalHistory {
            override fun showDetail(pack: Data) {
                company_profile_api().HistoryPackage(pack.packageX.packageNo, pack.userPackageNo, context){
                    val sheet = history_modal()
                    val mBundle = Bundle()
                    val jobData = Gson().toJson(it?.data)
                    mBundle.putString(history_modal.EXTRA_HISTORY_PACKAGE, jobData)
                    sheet.arguments = mBundle
                    Log.d("data", pack.orderNo.toString())

                    activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
                }


            }
        })
    }

    companion object {

    }
}