package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.Adapter

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iUpdateLocation
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Bottomsheet.iUpdateSkill
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iBasicInfoPage
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iCloseModal
import com.ciptakerjaarunika.kerjaloka.model.Data.City
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobLocation
import com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail.JobSkill
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.iCity
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.manage_profile.iEditBasic

class SkillAdapter(var data : List<JobSkill>, private val dataList: List<SkillFilter>, private val updateData: iUpdateSkill):
    RecyclerView.Adapter<SkillAdapter.EditCity>()
{
    inner class EditCity(view: View): RecyclerView.ViewHolder(view){
        var item: TextView
        var checkbox :CheckBox

        init {
            item = view.findViewById(R.id.txt_location)
            checkbox = view.findViewById(R.id.check_location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCity {
        val view = View.inflate(parent.context, R.layout.item_location, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return EditCity(view)
    }

    override fun onBindViewHolder(holder: EditCity, position: Int) {
        val currentItem = dataList[position]
        holder.item.text= "${currentItem.skillName}"

        var currentData = data.find { item -> item.skillNo == currentItem.skillNo}
        holder.checkbox.setOnClickListener {
            if(currentData != null){
                data = data.toMutableList()?.apply {
                    remove(currentData)
                }!!
            }
            else{
                data += JobSkill(null, null, currentItem.skillName,currentItem.skillNo)
            }
            updateData.updateSkill(data)
        }
        holder.checkbox.isChecked = currentData != null
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}