package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Listener

import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.Data
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.DataCount

interface JobDetail {
    fun jobDetail(jobDetail: DataCount)
    fun shareJob(shareJob: DataCount)
}