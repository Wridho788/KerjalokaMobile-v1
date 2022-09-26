package com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model

import com.ciptakerjaarunika.kerjaloka.model.Data.City
import com.ciptakerjaarunika.kerjaloka.model.Test.ShortQuestions
import com.ciptakerjaarunika.kerjaloka.model.Test.Tests


data class rjob_model(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: List<rJobModel>,
)

data class rJobModel(
    val companyNo: Long,
    val jobNo: Long,
    val jobPosition: String,
    val jobLocation: String,
    val logo: String,
    val status: String,
    val link: String,
    val companyName: String,
    val createdOn: String,
)

data class rJobDetailResponse(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: rJobDetailModel
)

data class rJobDetailModel(
    val companyNo: Long,
    val jobNo: Long,
    val jobPosition: String,
    val jobLocation: List<jobLocation>,
    val link: String,
    val company: company,
    val jobDescription: String,
    val jobTitle: List<jobTitle>,
    val jobMinExperience: Int,
    val jobField: jobField?,
    val jobRole: jobRole?,
    val createdOn: String,
    val job: List<job>,
    val jobSalaryMax: String,
    val jobSalaryMin: String,
    val companyjob: company,
    val bookmarked: Boolean?,
    val applied: Boolean?,

    val jobShortQuestion : List<ShortQuestions>,
    val jobTests: List<Tests>,
)

data class jobLocation(
    val label : String,
    val cityNo : Int,
    val cityName : String,
)

data class jobField(val fieldName: String)
data class jobTitle(val titleName: String)
data class jobRole(val jobRoleName: String)

data class company(
    val logo: String,
    val companyName: String,
    val city: City,
    val province: province
)

data class job(
    val jobNo: Long,
    val jobPosition: String,
    val createdOn: String,
    val company: companies
)

data class companies(
    val companyNo: Long,
    val companyName: String,
    val logo: String,
    val location: locationCompany
)

data class locationCompany(
    val city: String,
    val province: String,
)

data class province(
    val provinceName: String
)

