package com.ciptakerjaarunika.kerjaloka.Company.Package

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.myPackageAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.Company.Package.Model.Data
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.company_profile_api
import com.google.gson.Gson

class company_package_list : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<myPackageAdapter.myPackage>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_company_package_list, container, false)
        val back_button = view.findViewById<ImageView>(R.id.btn_back)
        back_button.setOnClickListener {
            activity?.onBackPressed()
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        company_profile_api().CompanyGetPackageData(context) {
            if (it != null) {
                val recyclerView = view.findViewById<RecyclerView>(R.id.myPackageRecycler)
                layoutManager = LinearLayoutManager(activity)
                recyclerView.layoutManager = layoutManager
                adapter = it.data.let { it1 -> assignAdapter(it1) }
                recyclerView.adapter = adapter
            }
        }


        return view
    }

    internal fun assignAdapter(list: List<Data>): myPackageAdapter {
        return myPackageAdapter(requireContext(), list, object : ShowModalHistory {
            override fun showDetail(pack: Data) {
                company_profile_api().HistoryPackage(
                    pack.packageX.packageNo,
                    pack.userPackageNo,
                    context
                ) {
                    val sheet = history_modal()
                    val mBundle = Bundle()
                    val jobData = Gson().toJson(it?.data)
                    mBundle.putString(history_modal.EXTRA_HISTORY_PACKAGE, jobData)
                    sheet.arguments = mBundle

                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }


            }
        })
    }
}