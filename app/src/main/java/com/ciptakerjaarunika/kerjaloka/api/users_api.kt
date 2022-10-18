package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ciptakerjaarunika.kerjaloka.Company.Profile.DeactivatedAccount
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.CategoryList
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.RatingSendedResponse
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.editReviewRequest
import com.ciptakerjaarunika.kerjaloka.enum.Role
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.notifResponse
import org.json.JSONObject
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
    interface companySendReview {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("company/rating/send")
        fun sendReview(@Body editReviewReq: editReviewRequest) : Call<sendReviewResponse>
    }
    interface jobseekerSendReview {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/rating/send")
        fun sendReview(@Body editReviewReq: editReviewRequest) : Call<sendReviewResponse>
    }

    fun SendReview(UserNo: Long, Message: String, Rating: Int, ProRating: ArrayList<CategoryList>, ConRating: ArrayList<CategoryList>, context: Context?, onResult: (sendReviewResponse?) -> Unit){
        if(SessionManager(context).user!!.roleNo == Role.Jobseekers.value){
            val retrofit = ServiceBuilder(context).POST(jobseekerSendReview::class.java)

            retrofit.sendReview(editReviewRequest(UserNo, Message, Rating, ProRating, ConRating))
                .enqueue(
                    object : Callback<sendReviewResponse> {
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
        else {
            val retrofit = ServiceBuilder(context).POST(companySendReview::class.java)

            retrofit.sendReview(editReviewRequest(UserNo, Message, Rating, ProRating, ConRating))
                .enqueue(
                    object : Callback<sendReviewResponse> {
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

    data class  deactivatedResponse(val code :Int, val message : String)
    interface deactivatedAccount {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("users/account/deactivate")
        fun deactivatedAccount(@Body deactivatedAccount: DeactivatedAccount) : Call<deactivatedResponse>
    }

    fun DeactiveAccount(password : String, context: Context?, onResult: (deactivatedResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(deactivatedAccount::class.java)

        retrofit.deactivatedAccount(DeactivatedAccount(password)).enqueue(
            object : Callback<deactivatedResponse>{
                override fun onResponse(
                    call: Call<deactivatedResponse>,
                    response: Response<deactivatedResponse>
                ) {
                    if (response.body() != null) {
                        SessionManager(context).access_token = null
                        SessionManager(context).user = null
                        onResult(response.body())
                    } else {
                        val data: String = response.errorBody()!!.string()
                        try {
                            val jObjError = JSONObject(data)
                            SessionManager(context).access_token = null
                            SessionManager(context).user = null
                            Toast.makeText(context, "Password Salah", Toast.LENGTH_SHORT).show()
                            Log.d("response json err", jObjError.toString())
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                        Log.d("response respon err", response.toString())
                    }
                }

                override fun onFailure(call: Call<deactivatedResponse>, t: Throwable) {
                    onResult(null)
                    Log.d("res err", t.toString())
                }
            }
        )
    }
}