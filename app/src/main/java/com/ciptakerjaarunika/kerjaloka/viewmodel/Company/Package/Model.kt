package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package

data class getHistoryResponse(
    val code: Int, val data: List<pckHistory>, val message: String
)

data class pack(
    val activatedOn: String,
    val companyNo: Long,
    val credit: Int,
    val expiredOn: String,
    val order: List<order>,
    val orderNo: Int,
    val packages: List<packages>,
    val period: Int,
    val startOn: String,
    val userPackageNo: Int
)

data class pckHistory(
    val actionOn: String,
    val creditValue: Int,
    val packageName: String,
    val userPackageLogDescription: String
)

data class order(
    val boughtOn: String,
    val buyerNo: Long,
    val companyNo: Long?,
    val externalIdInvoice: String,
    val idInvoice: String,
    val invoiceUrl: String,
    val itemNo: Int,
    val itemTypeNo: Int,
    val orderNo: Int,
    val orderStatusNo: Int,
    val paidOn: String?,
    val price: Int,
    val promoCode: String?,
    val quantity: Int,
    val totalPaid: Int
)

data class packages(
    val createdBy: Long,
    val createdOn: String,
    val expiredOn: String?,
    val isDiscoverable: Boolean = true,
    val isSuspended: Boolean = false,
    val isSystem: Boolean = true,
    val packageCredit: Int,
    val packageDescription: String,
    val packageDiscountedPrice: Int?,
    val packageName: String,
    val packageNo: Int,
    val packagePeriod: Int,
    val packagePrice: Int,
    val packageTypeNo: Int,
    val recurring: Boolean,
    val startOn: String,
    val targetView: Int,
    val updatedBy: Long?,
    val updatedOn: String?
)