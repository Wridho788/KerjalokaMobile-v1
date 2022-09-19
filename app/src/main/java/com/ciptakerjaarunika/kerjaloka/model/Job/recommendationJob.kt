package com.ciptakerjaarunika.kerjaloka.model.Job

import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.job
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.location
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.locationCompany
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.rating
import com.ciptakerjaarunika.kerjaloka.model.Data.Country
import java.math.BigDecimal

data class RecommendationJob(
    val companyNo : Long,
    val jobPosition : String,
    val jobNo : Long,
    val jobSalaryMin : BigDecimal,
    val jobSalaryMax : BigDecimal,
    val jobLocation : List<JobLocation>,
    val createdOn : String,
    val link : String,
    val bookmarked : Boolean,
    val company: Company,
)
data class JobLocation(
    val cityNo : Int,
    val location : String,
)

data class Company(
    val status : String,
    val logo : String,
    val url : String,
    val country : Country,
    val companyName : String,
    val companyDescription : String,
    val location: locationCompany
)
