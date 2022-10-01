package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ChangeUsernameRequest
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.CategoryList
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.RatingSendedResponse
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.editReviewRequest
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ratingSended_response
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.notifResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

class UsersAPI{
    interface userNotification{
        @GET("users/notifications/get")
        fun getNotification(): Call<notifResponse>
    }
    fun GetNotification(context: Context?, onResult: (notifResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(userNotification::class.java)

        retrofit.getNotification().enqueue(
            object : Callback<notifResponse>{
                override fun onResponse(
                    call: Call<notifResponse>,
                    response: Response<notifResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<notifResponse>, t: Throwable) {
                    Log.e("error", t.toString())
                    onResult(null)
                }

            }
        )
    }

    interface compGetSendedReview{
        @GET("users/review/getAllRatingSended")
        fun compSendedReview(@Query("sortByNewest")SortByNewest: Boolean): Call<RatingSendedResponse>
    }
    fun CompSendedReview(SortByNewest: Boolean, context: Context?, onResult: (RatingSendedResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(compGetSendedReview::class.java)

        retrofit.compSendedReview(SortByNewest).enqueue(
            object : Callback<RatingSendedResponse>{
                override fun onResponse(
                    call: Call<RatingSendedResponse>,
                    response: Response<RatingSendedResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<RatingSendedResponse>, t: Throwable) {
                    Log.e("asd", t.toString())
                    onResult(null)
                }

            }
        )
    }


    data class  sendReviewResponse(val code :Int, val data : String, val errorCode: Int, val message: String)
    interface sendReview {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("company/rating/send")
        fun sendReview(@Body editReviewReq: editReviewRequest) : Call<sendReviewResponse>
    }

    fun SendReview(UserNo: Long, Message: String, Rating: Int, ProRating: ArrayList<CategoryList>, ConRating: ArrayList<CategoryList>, context: Context?, onResult: (sendReviewResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(sendReview::class.java)

        retrofit.sendReview(editReviewRequest(UserNo, Message, Rating, ProRating, ConRating)).enqueue(
            object : Callback<sendReviewResponse>{
                override fun onResponse(
                    call: Call<sendReviewResponse>,
                    response: Response<sendReviewResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<sendReviewResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    data class  deleteReviewResponse(val code :Int, val data : String, val errorCode: Int, val message: String)
    interface compDeleteReview{
        @GET("users/rating/delete")
        fun deleteSendedReview(@Query("userRatingNo")userRatingNo: Int): Call<deleteReviewResponse>
    }
    fun DeleteSendedReview(userRatingNo: Int, context: Context?, onResult: (deleteReviewResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(compDeleteReview::class.java)

        retrofit.deleteSendedReview(userRatingNo).enqueue(
            object : Callback<deleteReviewResponse>{
                override fun onResponse(
                    call: Call<deleteReviewResponse>,
                    response: Response<deleteReviewResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<deleteReviewResponse>, t: Throwable) {
                    Log.e("asd", t.toString())
                    onResult(null)
                }

            }
        )
    }
}