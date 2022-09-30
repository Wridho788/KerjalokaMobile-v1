package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ChangeUsernameRequest
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


    data class  editReviewResponse(val code :Int, val data : String, val errorCode: Int, val message: String)
    interface editReview {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("users/change/username")
        fun editReview(@Body editReviewReq: editReviewRequest) : Call<editReviewResponse>
    }

    fun EditReview(UserNo: Long, Message: String, Rating: Int, ProRating: List<Int>, ConRating: List<Int>, context: Context?, onResult: (editReviewResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(editReview::class.java)

        retrofit.editReview(editReviewRequest(UserNo, Message, Rating, ProRating, ConRating)).enqueue(
            object : Callback<editReviewResponse>{
                override fun onResponse(
                    call: Call<editReviewResponse>,
                    response: Response<editReviewResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<editReviewResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}