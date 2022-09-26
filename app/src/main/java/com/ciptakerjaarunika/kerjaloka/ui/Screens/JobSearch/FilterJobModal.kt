package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch

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
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.JobAPI
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.job
import com.ciptakerjaarunika.kerjaloka.model.Data.*
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerExperiences
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Adapter.LocationAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.industri_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.size_company_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.ExperienceLevelAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.FilterLocationAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.JobTypeAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.SkillAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip

class FilterJobModal(iSearchJob: iSearchJob) : SuperBottomSheetFragment(), iUpdateValue {
    private var locations : List<LocationFilter>? = listOf()
    private var job_types : List<JobTypeFilter>? = listOf()
    private var skills : List<SkillFilter>? = listOf()
    private var experienceLevel : List<ExperienceLevelFilter>? = listOf()

    private var filterNo : Int = 1
//  private var experiences : List<JobseekerExperiences> = listOf()

    private var locationSelected : List<LocationFilter> = listOf()
    private var jobTypeSelected : List<JobTypeFilter> = listOf()
    private var skillSelected : List<SkillFilter> = listOf()
    private var experienceLevelSelected : List<ExperienceLevelFilter> = listOf()

    private var loadingData = 4;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_filter_job, container, false)
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Chip>(R.id.location).setOnClickListener{ChangeList(1)}
        view.findViewById<Chip>(R.id.job_type).setOnClickListener{ChangeList(2)}
        view.findViewById<Chip>(R.id.skill).setOnClickListener{ChangeList(3)}
        view.findViewById<Chip>(R.id.experience).setOnClickListener{ChangeList(4)}
        view.findViewById<Chip>(R.id.salary).setOnClickListener{ChangeList(5)}

        DataAPI().GetLocations(context){res ->
            loadingData -= 1;
            locations = res
            var recycle = view.findViewById<RecyclerView>(R.id.list_filter)

            recycle.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = FilterLocationAdapter(locations!!, context, this@FilterJobModal)
            }
            loadingDone()
        }

        DataAPI().GetJobTypes(context){res ->
            loadingData -= 1;
            job_types = res
            loadingDone()
        }
        DataAPI().GetSkill(context){res ->
            loadingData -= 1;
            skills = res
            loadingDone()
        }
        DataAPI().GetExperienceLevel(context){res ->
            loadingData -= 1;
            experienceLevel = res
            loadingDone()
        }
        view.findViewById<Chip>(R.id.salary).setOnClickListener{ChangeList(5)}
    }

    fun ChangeList(filterType : Int){
        var recycle = view?.findViewById<RecyclerView>(R.id.list_filter)

        when(filterType){
            1 ->{
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = FilterLocationAdapter(locations!!, context, this@FilterJobModal)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            2 ->{
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = JobTypeAdapter(job_types!!, context, this@FilterJobModal)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            3 ->{
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SkillAdapter(skills!!, context, this@FilterJobModal)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            4 ->{
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ExperienceLevelAdapter(experienceLevel!!, context, this@FilterJobModal)
                }
                recycle?.adapter?.notifyDataSetChanged()
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

    override fun updateLocations(location : LocationFilter) {
        val indexOf =  locationSelected.indexOfFirst { loc -> loc.locationsNo == location.locationsNo && location.checked == false }
        if(locationSelected.size == 0 || locationSelected.any{loc -> loc.locationsNo == location.locationsNo} == false){
            locationSelected += location
        }
        else if(indexOf >= 0){
            locationSelected = locationSelected.drop(indexOf)
        }
        Log.d("locations", locationSelected.toString())
    }

    override fun updateSkills(skills: SkillFilter) {
        val indexOf =  skillSelected.indexOfFirst { loc -> loc.skillNo == skills.skillNo && skills.checked == false }

        if(skillSelected.size == 0 || !skillSelected!!.any { loc -> loc.skillNo == skills.skillNo}){
            skillSelected += skills
        }
        else if(indexOf >= 0){
            skillSelected = skillSelected.drop(indexOf)
        }
        skillSelected = skillSelected.filter { data-> data.checked == true }
    }

    override fun updateJobTypes(jobTypes: JobTypeFilter) {
        val indexOf =  jobTypeSelected.indexOfFirst { loc -> loc.jobTypeNo == jobTypes.jobTypeNo && jobTypes.checked == false }

        if(jobTypeSelected.size == 0 || !jobTypeSelected!!.any { loc -> loc.jobTypeNo == jobTypes.jobTypeNo}){
            jobTypeSelected += jobTypes
        }
        else if(indexOf >= 0){
            jobTypeSelected = jobTypeSelected.drop(indexOf)
        }
        jobTypeSelected = jobTypeSelected.filter { data-> data.checked == true }
    }
    override fun updateExperienceLevel(experienceLevel: ExperienceLevelFilter) {
        val indexOf =  experienceLevelSelected.indexOfFirst { loc -> loc.experienceLevelNo == experienceLevel.experienceLevelNo && experienceLevel.checked == false }

        if(experienceLevelSelected.size == 0 || !experienceLevelSelected!!.any { loc -> loc.experienceLevelNo == experienceLevel.experienceLevelNo}){
            experienceLevelSelected += experienceLevel
        }
        else if(indexOf >= 0){
            experienceLevelSelected = experienceLevelSelected.drop(indexOf)
        }
        experienceLevelSelected = experienceLevelSelected.filter { data-> data.checked == true }
    }
    fun loadingDone(){
        if(loadingData ==0){
            view?.findViewById<LinearLayout>(R.id.spinnerFilter)?.visibility = GONE
            view?.findViewById<RecyclerView>(R.id.list_filter)?.visibility = VISIBLE
        }
    }
}

interface iUpdateValue {
    fun updateLocations(locations: LocationFilter)
    fun updateSkills(skills : SkillFilter)
    fun updateJobTypes(jobTypes : JobTypeFilter)
    fun updateExperienceLevel(experienceLevel: ExperienceLevelFilter)
}