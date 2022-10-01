package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.Listener

import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.Data

interface JobDetail {
    fun jobDetail(jobDetail: Data)
    fun shareJob(shareJob: Data)
}