package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Profile.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class ManageProfileAPI {
    data class editAboutMeRequest(
        val jobseekerAbout : String
    )
    interface editAboutMe {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/edit/about")
        fun sendData(@Body newData: editAboutMeRequest): Call<Any>
    }

    fun EditAboutMe(aboutMe : String, context: Context?,onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(editAboutMe::class.java)

        retrofit.sendData(editAboutMeRequest(aboutMe)).enqueue(
            object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Any>, response: Response<Any>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    data class editAdditionalRequest(
        val maritalNo : Int?,
        val religionNo : Int?,
        val postalCode : String?,
        val placeOfBirth : String?,
        val ethnics : String?,
        val residentNo : Int?,
        val telegramId : String?,
        val instagramId : String?,
    )
    interface editAdditional {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/edit/additional")
        fun sendData(@Body newData: editAdditionalRequest): Call<Any>
    }

    fun EditAdditional(data: editAdditionalRequest, context: Context?,onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(editAdditional::class.java)

        retrofit.sendData(data).enqueue(
            object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Any>, response: Response<Any>
                ) {
                    onResult(response.body())
                }
            }
        )
    }
}