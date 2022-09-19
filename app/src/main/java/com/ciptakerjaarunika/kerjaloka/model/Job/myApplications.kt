package com.ciptakerjaarunika.kerjaloka.model.Job

import com.ciptakerjaarunika.kerjaloka.model.User.Company
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.companies
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.job
import java.util.*

data class myApplicationsResponse(
    val data :  List<ApplicationData>,
)
data class ApplicationData(
    val application : ApplicationJob,
    val job : com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.job,
    val tests : List<ApplicationTest>,
)
data class ApplicationJob(
    val applicationNo : Long,
    val applyOn : String,
)
data class ApplicationTest(
    val test : Any?,
    val testResult : Any?,
)