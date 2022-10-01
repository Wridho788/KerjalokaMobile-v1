package com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model


import com.google.gson.annotations.SerializedName

data class RatingSendedResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: List<DataX>,
    @SerializedName("errorCode")
    var errorCode: Int,
    @SerializedName("message")
    var message: String
)

data class editReviewRequest(
    var UserNo: Long,
    var Message: String,
    var Rating: Int,
    var ProRating: List<CategoryList>,
    var ConRating: List<CategoryList>
)
data class CategoryList (
    val categoryNo : Int
        )