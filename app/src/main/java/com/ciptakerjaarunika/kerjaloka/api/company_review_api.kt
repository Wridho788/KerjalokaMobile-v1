package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ratingSended_response
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.review_response
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.sendResponse
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.send_Request
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.send_review_response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*


class CompanyReviewAPI {
    interface CompanyReviewAPIList {
        @GET("/users/rating/get")
        fun getCompanyReview(@Query("userNo") userNo: Long?): Call<review_response>
    }

    fun getCompanyReviewAsync(
        context: Context?, userNo: Long?, onResult: (review_response?)->Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(CompanyReviewAPIList::class.java)

            retrofit.getCompanyReview(userNo).enqueue(
                object : Callback<review_response> {
                    override fun onResponse(
                        call: Call<review_response>,
                        response: Response<review_response>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<review_response>, t: Throwable) {
                        Log.d("error",t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }
}

class CompanyMyReviewAPI {
    interface CompanyReviewAPIList {
        @GET("/company/rating/myReview")
        fun getCompanyReview(): Call<ratingSended_response>
    }

    fun getCompanyReviewAsync(
        context: Context?, onResult: (ratingSended_response?)->Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(CompanyReviewAPIList::class.java)

            retrofit.getCompanyReview().enqueue(
                object : Callback<ratingSended_response> {
                    override fun onResponse(
                        call: Call<ratingSended_response>,
                        response: Response<ratingSended_response>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<ratingSended_response>, t: Throwable) {
                        Log.d("error",t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }
}

class CanSendReview {
    interface SectionSendReviewResponse {
        @GET("/users/rating/canSend")
        fun getSendReview(@Query("userNo") userNo: Long): Call<send_review_response>
    }

    fun getSendReviewAsync(context: Context?, userNo: Long, onResult: (send_review_response?) -> Unit){
        if(context != null){
            val retrofit = ServiceBuilder(context).GET(SectionSendReviewResponse::class.java)

            retrofit.getSendReview(userNo).enqueue(
                object : Callback<send_review_response> {
                    override fun onResponse(
                        call: Call<send_review_response>,
                        response: Response<send_review_response>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<send_review_response>, t: Throwable) {
                        Log.d("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }
}

class SendReviewAPI {
    interface SendReviewAPI {
        @Headers("Content-Type: application/json",
            "Accept: application/json")
        @POST("/jobseeker/rating/send")
        fun sendReview(@Body sendRequest: send_Request): Call<sendResponse>
    }

    fun SendReviewPost(context: Context?, sendRequest: send_Request, onResult: (sendResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(SendReviewAPI::class.java)

        retrofit.sendReview(sendRequest).enqueue(
            object : Callback<sendResponse> {
                override fun onResponse(
                    call: Call<sendResponse>,
                    response: Response<sendResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<sendResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
            }
        )
    }
}
