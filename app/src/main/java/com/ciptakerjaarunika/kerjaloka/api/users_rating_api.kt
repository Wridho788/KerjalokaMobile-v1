package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ChangeUsernameRequest
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.CategoryList
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.RatingSendedResponse
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.appealReviewRequest
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.editReviewRequest
import com.ciptakerjaarunika.kerjaloka.Company.Profile.ratingSended_response
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.notifResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.io.File

class UserRatingAPI{



    data class  appealReviewResponse(val code :Int, val data : String, val errorCode: Int, val message: String)

    interface sendAppeal{
        @Multipart
        @POST("users/review/sendAppeal")
        fun sendData(@Part("ratingBy") RatingBy : Long,@Part("appealMessage") AppealMessage : String, @Part file : MultipartBody.Part?  ): Call<appealReviewResponse>
    }
    fun SendAppeal(RatingBy: Long, AppealMessage: String, File: File?, context: Context?, onResult: (appealReviewResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POSTFILE(sendAppeal::class.java)

        val requestFile: RequestBody? = File?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val file: MultipartBody.Part? =
            requestFile?.let { MultipartBody.Part.createFormData("file", File?.name, it) }

        retrofit.sendData(RatingBy, AppealMessage, file).enqueue(
            object : Callback<appealReviewResponse>{
                override fun onResponse(
                    call: Call<appealReviewResponse>,
                    response: Response<appealReviewResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<appealReviewResponse>, t: Throwable) {
                    Log.e("asd", t.toString())
                    onResult(null)
                }

            }
        )
    }
}