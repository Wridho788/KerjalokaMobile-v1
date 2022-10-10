package com.ciptakerjaarunika.kerjaloka.model.Job.CompanyJobDetail

data class JobTest(
    var companyNo: Long?,
    var createdByUserNo: Long?,
    var createdOn: String?,
    var isPublic: Boolean?,
    var isSpecial: Boolean?,
    var isTakedown: Boolean?,
    var limitReviewDay: Any?,
    var maxScore: Double?,
    var orderNo: Any?,
    var refOwner: Any?,
    var refTestNo: Any?,
    var reviewedAmount: Any?,
    var testDuration: Int?,
    var testEnabled: Boolean?,
    var testHint: Any?,
    var testLink: Any?,
    var testName: String?,
    var testNo: Long,
    var testPeriod: Int?
)