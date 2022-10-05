package com.ciptakerjaarunika.kerjaloka.model.Profile

import com.ciptakerjaarunika.kerjaloka.model.Data.*
import com.ciptakerjaarunika.kerjaloka.model.User.Jobseeker
import com.ciptakerjaarunika.kerjaloka.model.User.JobseekerAdditional
import com.ciptakerjaarunika.kerjaloka.model.User.User

data class JobseekerRecord(
    val recordNo : Int,
    val statusChangeOn : String,
    val description : String,
    val ownerName : String,
    val appeal: RecordAppeal?
)

data class RecordAppeal(
    val description: String,
    val evidence: List<RecordEvidence>
)
data class RecordEvidence(
    val recordNo : Int?,
    val recordAppealNo : Int?,
    val image : String,
    val createdOn :String,
    val createdBy : Long,
)


