package com.ciptakerjaarunika.kerjaloka.Company.Package.Model


import com.google.gson.annotations.SerializedName

data class Package(
    @SerializedName("createdBy")
    val createdBy: Int,
    @SerializedName("createdOn")
    val createdOn: String,
    @SerializedName("expiredOn")
    val expiredOn: String?,
    @SerializedName("isDiscoverable")
    val isDiscoverable: Boolean,
    @SerializedName("isSuspended")
    val isSuspended: Boolean,
    @SerializedName("isSystem")
    val isSystem: Boolean,
    @SerializedName("packageCredit")
    val packageCredit: Int,
    @SerializedName("packageDescription")
    val packageDescription: String,
    @SerializedName("packageDiscountedPrice")
    val packageDiscountedPrice: Int?,
    @SerializedName("packageName")
    val packageName: String,
    @SerializedName("packageNo")
    val packageNo: Int,
    @SerializedName("packagePeriod")
    val packagePeriod: Int,
    @SerializedName("packagePrice")
    val packagePrice: Int,
    @SerializedName("packageTypeNo")
    val packageTypeNo: Int,
    @SerializedName("recurring")
    val recurring: Boolean,
    @SerializedName("startOn")
    val startOn: String,
    @SerializedName("updatedBy")
    val updatedBy: Long?,
    @SerializedName("updatedOn")
    val updatedOn: String?
)