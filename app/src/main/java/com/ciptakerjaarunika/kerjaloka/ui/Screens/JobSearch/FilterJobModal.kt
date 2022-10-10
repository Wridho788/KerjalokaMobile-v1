package com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.model.Data.ExperienceLevelFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.ExperienceLevelAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.FilterLocationAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.JobTypeAdapter
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.Adapter.SkillAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip

class FilterJobModal(
    val iSearchJob: iSearchJob,
    val locationParent: List<Int>,
    val jobTypeParent: List<Int>,
    val skillParent: List<Int>,
    val experienceLevelParent: List<Int>,
    val salaryMinParent: Int?,
    val salaryMaxParent: Int?
) : SuperBottomSheetFragment() {
    private var locations : List<LocationFilter>? = listOf()
    private var job_types : List<JobTypeFilter>? = listOf()
    private var skills : List<SkillFilter>? = listOf()
    private var experienceLevel : List<ExperienceLevelFilter>? = listOf()
    private var salaryMin : Int? = null
    private var salaryMax : Int? = null

    private var filterNo : Int = 1

    private var loadingData = 4;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_filter_job, container, false)
    }

//    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("SalaryMin", salaryMinParent.toString())

        view.findViewById<Chip>(R.id.location).setOnClickListener{ChangeList(1)}
        view.findViewById<Chip>(R.id.job_type).setOnClickListener{ChangeList(2)}
        view.findViewById<Chip>(R.id.skill).setOnClickListener{ChangeList(3)}
        view.findViewById<Chip>(R.id.experience).setOnClickListener{ChangeList(4)}
        view.findViewById<Chip>(R.id.salary).setOnClickListener{ChangeList(5)}
        view.findViewById<TextView>(R.id.btn_clear_filter).setOnClickListener{
            ClearList()
        }
        var searchInput = view.findViewById<SearchView>(R.id.search_filter)

        DataAPI().GetLocations(context){res ->
            loadingData -= 1;
            locations = res
            var recycle = view.findViewById<RecyclerView>(R.id.list_filter)

            recycle.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = FilterLocationAdapter(locations!!, context)
            }
            view.findViewById<Chip>(R.id.location).setBackgroundColor(R.color.danger_300)
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
        var salaryMin_txt = view.findViewById<EditText>(R.id.min_salary)
        var salaryMax_txt = view.findViewById<EditText>(R.id.max_salary)

        salaryMin_txt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable) {
                salaryMin = salaryMin_txt.text.toString().toIntOrNull()
            }
        })

        salaryMax_txt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable) {
                salaryMax = salaryMax_txt.text.toString().toIntOrNull()
            }
        })


        view.findViewById<Chip>(R.id.salary).setOnClickListener{ChangeList(5)}
        view.findViewById<MaterialButton>(R.id.btn_konfirmasi).setOnClickListener{
            var selectedLocation : List<Int> = listOf()
            locations?.forEach { data-> if(data.checked == true){selectedLocation += data.locationsNo}  }
            iSearchJob.updateLocationSelected(selectedLocation)

            var selectedJobType : List<Int> = listOf()
            job_types?.forEach { data-> if(data.checked == true){selectedJobType += data.jobTypeNo}  }
            iSearchJob.updateJobTypeSelected(selectedJobType)

            var selectedSkill : List<Int> = listOf()
            skills?.forEach { data-> if(data.checked == true){selectedSkill += data.skillNo}  }
            iSearchJob.updateSkillSelected(selectedSkill)

            var selectedExperienceLevel : List<Int> = listOf()
            experienceLevel?.forEach { data-> if(data.checked == true){selectedExperienceLevel += data.experienceLevelNo}  }
            iSearchJob.updateExperienceSelected(selectedExperienceLevel)

            iSearchJob.updateSalaryMin(salaryMin)
            iSearchJob.updateSalaryMin(salaryMax)

            iSearchJob.SearchJobs()
            this.dismiss()
        }
        var recycle = view?.findViewById<RecyclerView>(R.id.list_filter)

        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("Input", newText.toString())
                val keyword = newText.toString().toLowerCase()
                if (newText.isNullOrEmpty()) {
                    ChangeList(filterNo)
                } else {
                    when (filterNo) {
                        1 -> {
                            var temp = locations?.filter { data ->
                                "${data.city}, ${data.province}".toLowerCase().contains(keyword)
                            }
                            recycle?.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = FilterLocationAdapter(temp!!, context)
                            }
                            recycle?.adapter?.notifyDataSetChanged()
                        }
                        2 -> {
                            var temp = job_types?.filter{ data -> data.jobTypeName.toLowerCase().contains(keyword) }
                            recycle?.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = JobTypeAdapter(temp!!, context)
                            }
                            recycle?.adapter?.notifyDataSetChanged()
                        }
                        3 -> {
                            var temp = skills?.filter{ data -> data.skillName.toLowerCase().contains(keyword) }
                            recycle?.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = SkillAdapter(temp!!, context)
                            }
                            recycle?.adapter?.notifyDataSetChanged()
                        }
                        4 -> {
                            var temp = experienceLevel?.filter{ data -> data.experienceLevelName.toLowerCase().contains(keyword) }
                            recycle?.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = ExperienceLevelAdapter(temp!!, context)
                            }
                            recycle?.adapter?.notifyDataSetChanged()
                        }
                    }
                }
                return true;
            }
        })
    }

    fun ChangeList(filterType : Int){
        var recycle = view?.findViewById<RecyclerView>(R.id.list_filter)
        val salaryContainer = view?.findViewById<LinearLayout>(R.id.salary_filter_container)
        salaryContainer?.visibility = GONE
        view?.findViewById<RecyclerView>(R.id.list_filter)?.visibility = VISIBLE
        view?.findViewById<SearchView>(R.id.search_filter)?.visibility = VISIBLE
        this.filterNo = filterType

        when(filterType){
            1 ->{
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = FilterLocationAdapter(locations!!, context)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            2 ->{
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = JobTypeAdapter(job_types!!, context)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            3 ->{
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SkillAdapter(skills!!, context)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            4 ->{
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ExperienceLevelAdapter(experienceLevel!!, context)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            5->{
                salaryContainer?.visibility = VISIBLE
                view?.findViewById<SearchView>(R.id.search_filter)?.visibility = GONE
                view?.findViewById<RecyclerView>(R.id.list_filter)?.visibility = GONE
//                view?.findViewById<EditText>(R.id.min_salary)?.text = salaryMin
            }

        }
    }
    fun ClearList(){
        var recycle = view?.findViewById<RecyclerView>(R.id.list_filter)
        when(filterNo){
            1 ->{
                locations?.forEach { data-> data.checked = false }
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = FilterLocationAdapter(locations!!, context)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            2 ->{
                job_types?.forEach { data-> data.checked = false }
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = JobTypeAdapter(job_types!!, context)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            3 ->{
                skills?.forEach { data-> data.checked = false }
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SkillAdapter(skills!!, context)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            4 ->{
                experienceLevel?.forEach { data-> data.checked = false }
                recycle?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ExperienceLevelAdapter(experienceLevel!!, context)
                }
                recycle?.adapter?.notifyDataSetChanged()
            }
            5->{
                salaryMin = null
                salaryMax = null
                view?.findViewById<EditText>(R.id.min_salary)?.text = null
                view?.findViewById<EditText>(R.id.max_salary)?.text = null
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

    /*
    override fun updateLocations(location : LocationFilter) {
        val indexOf =  locationSelected.indexOfFirst { loc -> loc.locationsNo == location.locationsNo && location.checked == false }
        if(locationSelected.size == 0 || locationSelected.any{loc -> loc.locationsNo == location.locationsNo} == false){
            locationSelected += location
        }
        else if(indexOf >= 0){
            locationSelected = locationSelected.drop(indexOf)
        }
        var selected: List<Int> = listOf()
        locationSelected.filter { data-> data.checked == true }.forEach {
            selected += it.locationsNo
        }

        locations!!.forEach {
            if(location.locationsNo == it.locationsNo && location.checked == true){
                it.checked = true
                Log.d("Loc", location.toString())
            }
        }

        Log.d("Locations : ", locations?.filter { loc -> loc.checked == true }.toString())
        //iSearchJob.updateLocationSelected(selected)
    }
    */


    fun loadingDone(){
        if(loadingData ==0){
            Log.d("SalaryMin", salaryMinParent.toString())

            salaryMin = salaryMinParent
            salaryMax = salaryMaxParent
            if(salaryMin!= null) {
                view?.findViewById<EditText>(R.id.min_salary)?.setText(salaryMin.toString())
            }
            if(salaryMax != null) {
                view?.findViewById<EditText>(R.id.max_salary)?.setText(salaryMax.toString())
            }
            locations?.forEach {
                if(locationParent.any { item-> it.locationsNo == item }){
                    it.checked = true
                }
            }
            job_types?.forEach {
                if(jobTypeParent.any { item-> it.jobTypeNo == item }){
                    it.checked = true
                }
            }
            skills?.forEach {
                if(skillParent.any { item-> it.skillNo == item }){
                    it.checked = true
                }
            }
            experienceLevel?.forEach {
                if(experienceLevelParent.any { item-> it.experienceLevelNo == item }){
                    it.checked = true
                }
            }
            view?.findViewById<LinearLayout>(R.id.spinnerFilter)?.visibility = GONE
            view?.findViewById<RecyclerView>(R.id.list_filter)?.visibility = VISIBLE
        }
    }
}