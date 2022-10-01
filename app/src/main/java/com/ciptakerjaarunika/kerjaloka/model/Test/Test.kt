package com.ciptakerjaarunika.kerjaloka.model.Test

import java.math.BigDecimal

data class Tests(
    val testNo : Long?,
    val companyNo : Long,
    val testName : String,
    val testDuration : Int,
    val testPeriod : Int,
    val maxScore : BigDecimal,
    val testEnabled : Boolean,
    val isTakedown : Boolean,
    val createdByUserNo : Long,
    val createdOn : String,
    val reviewedAmount : Int?,
    val limitReviewDay : Int?,
    val refOwner : Long?,
    val refTestNo : Long?,
    val testLink : String,
    val isPublic : Boolean,
    val isSpecial : Boolean,
    val orderNo : Long,
)
