package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("activatedOn") val activatedOn: String?,
    @SerializedName("companyNo") val companyNo: Long,
    @SerializedName("credit") val credit: Int,
    @SerializedName("expiredOn") val expiredOn: String?,
    @SerializedName("order") val order: Order,
    @SerializedName("orderNo") val orderNo: Int,
    @SerializedName("package") val packageX: Package,
    @SerializedName("period") val period: Int,
    @SerializedName("startOn") val startOn: String?,
    @SerializedName("userPackageNo") val userPackageNo: Int
)