package com.ciptakerjaarunika.kerjaloka.Company.Package

import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel

data class pack(
    val activatedOn: String = "2022-05-21T09:52:02",
    val companyNo: Long = 20211027141022,
    val credit: Int = 69420,
    val expiredOn: String = "2023-05-21T00:00:00",
    val order: List<order>,
    val orderNo: Int = 167,
    val packages: List<packages>,
    val period: Int = 12,
    val startOn: String = "2022-05-21T00:00:00",
    val userPackageNo: Int = 110
)

data class pckHistory(
    val actionOn: String = "2022-05-21T10:07:34",
    val creditValue: Int = 1,
    val packageName: String = "Small Test Package",
    val userPackageLogDescription: String = "Add Test Test Tist (-1 Credit value)"
)

data class order(
    val boughtOn: String = "2021-11-11T09:18:20",
    val buyerNo: Long = 20211027141022,
    val companyNo: Long?,
    val externalIdInvoice: String = "PAY-INV167",
    val idInvoice: String = "618c7d6d59269dc3fb38207d",
    val invoiceUrl: String = "https://checkout-staging.xendit.co/web/618c7d6d59269dc3fb38207d",
    val itemNo: Int = 27,
    val itemTypeNo: Int = 4,
    val orderNo: Int = 167,
    val orderStatusNo: Int = 1,
    val paidOn: String? = "null",
    val price: Int = 200000,
    val promoCode: String? = "null",
    val quantity: Int = 1,
    val totalPaid: Int = 0
)

data class packages(
    val createdBy: Long = 0,
    val createdOn: String = "2021-10-23T14:25:49",
    val expiredOn: String?,
    val isDiscoverable: Boolean = true,
    val isSuspended: Boolean = false,
    val isSystem: Boolean = true,
    val packageCredit: Int = 5,
    val packageDescription: String = "Cheap 5 Credit Package",
    val packageDiscountedPrice: Int?,
    val packageName: String = "Small Offering Package",
    val packageNo: Int = 30,
    val packagePeriod: Int = 1,
    val packagePrice: Int = 50000,
    val packageTypeNo: Int = 2,
    val recurring: Boolean = false,
    val startOn: String = "2021-10-23T00:00:00",
    val targetView: Int = 0,
    val updatedBy: Long?,
    val updatedOn: String?
)