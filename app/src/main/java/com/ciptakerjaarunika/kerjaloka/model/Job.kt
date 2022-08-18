package com.ciptakerjaarunika.kerjaloka.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecommendationJob {
    @SerializedName("companyNo")
    @Expose
    var companyNo: Int? = null

    @SerializedName("jobPosition")
    @Expose
    var jobPosition: String? = null

    @SerializedName("jobNo")
    @Expose
    var jobNo: Int? = null

    @SerializedName("logo")
    @Expose
    var logo: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("link")
    @Expose
    var link: String? = null
}