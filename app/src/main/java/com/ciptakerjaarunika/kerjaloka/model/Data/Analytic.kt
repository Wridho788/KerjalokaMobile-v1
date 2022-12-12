package com.ciptakerjaarunika.kerjaloka.model.Data

data class CompanyAnalytic(
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: itemAnalytic
)

data class itemAnalytic(
    val item: Item,
    val analytic: Analytic,
)
data class Item(
    val jobNo: Long
)
data class Analytic(
    val clickCount: Long
)
