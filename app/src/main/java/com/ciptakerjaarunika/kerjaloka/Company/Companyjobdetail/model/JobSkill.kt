package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model


import com.google.gson.annotations.SerializedName

data class JobSkill(
    @SerializedName("jobNo")
    val jobNo: Long,
    @SerializedName("jobSkillNo")
    val jobSkillNo: Int,
    @SerializedName("skillName")
    val skillName: String,
    @SerializedName("skillNo")
    val skillNo: Int
)