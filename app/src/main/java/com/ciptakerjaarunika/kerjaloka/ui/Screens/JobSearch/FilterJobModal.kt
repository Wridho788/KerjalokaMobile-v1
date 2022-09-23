package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.model.Data.*
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperiences
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.LocationAdapter
import com.google.android.material.button.MaterialButton

class FilterJobModal() : SuperBottomSheetFragment() {
    private var locations : List<LocationFilter>? = listOf()
    private var job_types : List<JobTypeFilter>? = listOf()
    private var skills : List<SkillFilter>? = listOf()
    private var filterNo : Int = 1
//  private var experiences : List<JobseekerExperiences> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_filter_job, container, false)
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataAPI().GetLocations(context){res ->
            locations = res
        }

        DataAPI().GetJobTypes(context){res ->
            job_types = res
        }
        DataAPI().GetSkill(context){res ->
            skills = res
        }
        var recycle = view.findViewById<RecyclerView>(R.id.list_filter)
//        recycle.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = LocationAdapter(list_location!!, context, thisActivity)
//            listView.adapter = adapter
//        }
    }

    fun ChangeList(filterType : Int){

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

}