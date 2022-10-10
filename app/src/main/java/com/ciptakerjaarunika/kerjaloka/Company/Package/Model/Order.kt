package com.ciptakerjaarunika.kerjaloka.Company.Package.Model


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("boughtOn")
    val boughtOn: String,
    @SerializedName("buyerNo")
    val buyerNo: Long,
    @SerializedName("companyNo")
    val companyNo: Long?,
    @SerializedName("externalIdInvoice")
    val externalIdInvoice: String,
    @SerializedName("idInvoice")
    val idInvoice: String,
    @SerializedName("invoiceUrl")
    val invoiceUrl: String,
    @SerializedName("itemNo")
    val itemNo: Long,
    @SerializedName("itemTypeNo")
    val itemTypeNo: Int,
    @SerializedName("orderNo")
    val orderNo: Int,
    @SerializedName("orderStatusNo")
    val orderStatusNo: Int,
    @SerializedName("paidOn")
    val paidOn: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("promoCode")
    val promoCode: String?,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("totalPaid")
    val totalPaid: Int
)