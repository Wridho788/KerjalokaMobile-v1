package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.review_response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


class CompanyReviewAPI {
    interface CompanyReviewAPIList {
        @GET("/users/rating/get")
        fun getCompanyReview(@Query("userNo") userNo: Long): Call<review_response>
    }

    fun getCompanyReviewAsync(
        context: Context?, userNo: Long, onResult: (review_response?)->Unit
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

