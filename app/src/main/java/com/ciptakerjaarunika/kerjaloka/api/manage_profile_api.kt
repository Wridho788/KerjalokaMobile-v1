package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ciptakerjaarunika.kerjaloka.model.Interview.returnUploadChatPhotoApi
import com.ciptakerjaarunika.kerjaloka.model.Profile.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate
import java.time.LocalDateTime

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

    data class editBasicInfoRequest(
        val name : String?,
        val ktp : String?,
        val gender : Char?,
        val address : String?,
        val dateOfBirth : String?,
        val cityNo : Int?,
    )
    interface editBasicInfo {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/edit/basic")
        fun sendData(@Body newData: editBasicInfoRequest): Call<Any>
    }

    fun EditBasicInfo(data: editBasicInfoRequest, context: Context?,onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(editBasicInfo::class.java)

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

    data class jobseekerUploadPhotoResponse(
        val code : Int,
        val message:String,
        val data: String?
    )
    interface UploadPhoto {
        @Multipart
        @POST("jobseeker/mobile/editphoto")
        fun UploadPhoto(@Part photo : MultipartBody.Part): Call<jobseekerUploadPhotoResponse>
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun UploadPhoto(context: Context?, photo : MultipartBody.Part, onResult: (jobseekerUploadPhotoResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POSTFILE(UploadPhoto::class.java)
        retrofit.UploadPhoto(photo).enqueue(
            object : Callback<jobseekerUploadPhotoResponse> {
                override fun onFailure(call: Call<jobseekerUploadPhotoResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<jobseekerUploadPhotoResponse>,
                    response: Response<jobseekerUploadPhotoResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }
}