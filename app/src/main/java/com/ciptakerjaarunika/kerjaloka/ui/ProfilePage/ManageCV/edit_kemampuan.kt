package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ManageCV

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditKemampuanBinding
import com.ciptakerjaarunika.kerjaloka.enum.SkillScale
import com.ciptakerjaarunika.kerjaloka.model.Data.Skill
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerSkills
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.SkillAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseScale
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.ChooseSkill
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.google.android.material.chip.ChipGroup


class edit_kemampuan(var dataList: List<JobseekerSkills>?) : Fragment(), iEditKemampuan {
    private lateinit var binding : FragmentEditKemampuanBinding
    private var initialSkills : List<SkillFilter> = listOf()
    private var skills : List<SkillFilter> = listOf()
    private var skill: SkillFilter? = null
    private var scale : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditKemampuanBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        binding.jsEditSkillLevel.setOnClickListener {
            val sheet = ChooseScale(scale, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
        DataAPI().GetSkill(context){
            if (it != null) {
                skills = it
                initialSkills = it

                refreshSkill()
                binding.recycleView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = dataList?.let { it1 -> SkillAdapter(it1, context, this@edit_kemampuan) }
                }
            }
        }
        binding.btnAddSkill.setOnClickListener {
            binding.errorTxt.visibility = VISIBLE

            if(skill == null){
                binding.errorTxt.text = "Silahkan Pilih Skill"
            }
            else if(scale == null)  {
                binding.errorTxt.text = "Skill Level tidak boleh kosong"
            }
            else {
                binding.errorTxt.visibility = GONE
                dataList = dataList?.plus(
                    JobseekerSkills(
                        jobseekerNo = SessionManager(context).user!!.userNo,
                        scale = scale!!,
                        skillName =  skill!!.skillName,
                        skillNo = skill!!.skillNo
                    )
                )
                binding.recycleView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = dataList?.let { it1 -> SkillAdapter(it1, context, this@edit_kemampuan) }
                }
                binding.recycleView.adapter?.notifyDataSetChanged()
                skill = null
                scale = null
                binding.jsEditSkill.text = null
                binding.jsEditSkillLevel.text = null
                refreshSkill()
            }
        }

        binding.saveBtn.setOnClickListener {
            ManageProfileAPI().JobseekerEditSkills(dataList, context){
                if(it != null) {
                    Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
                    back()
                }
                else{
                    Toast.makeText(activity, "Terjadi kesalahan yang tidak diketahui", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun back(){
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction?.replace(id, profilepage(1), "Profile Page")
        fragmentTransaction?.commit()
    }

    override fun updateSkill(value: SkillFilter) {
        skill = value
        binding.jsEditSkill.text = value.skillName
    }

    override fun updateScale(value: Int) {
        scale = value
        binding.jsEditSkillLevel.text = SkillScale.values().find{scale -> scale.value == value}?.description
    }

    override fun removeSkill(value: JobseekerSkills) {
        dataList = dataList?.toMutableList()?.apply {
            remove(value)
        }
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = dataList?.let { it1 -> SkillAdapter(it1, context, this@edit_kemampuan) }
        }
        binding.recycleView.adapter?.notifyDataSetChanged()
        refreshSkill()
    }

    fun refreshSkill(){
        skills = initialSkills.filter {filt -> !dataList!!.any { data-> data.skillNo == filt.skillNo} }
        binding.jsEditSkill.setOnClickListener {
            val sheet = ChooseSkill(skill, skills, this)
            activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
        }
    }
}
interface iEditKemampuan{
    fun updateSkill(value : SkillFilter)
    fun updateScale(value : Int)
    fun removeSkill(value : JobseekerSkills)
}