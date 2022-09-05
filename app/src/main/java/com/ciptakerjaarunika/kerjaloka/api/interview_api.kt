package com.ciptakerjaarunika.kerjaloka.api


import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Interview.conmpany_interview_list_api
import com.ciptakerjaarunika.kerjaloka.model.Interview.returnUploadChatPhotoApi
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.io.File

class InterviewAPI {
    interface CompanyGetInterviewList {
        @GET("company/application/getinterview")
        fun getJobHome(): Call<conmpany_interview_list_api>
    }
     fun CompanyGetInterviewList(context: Context?, onResult: (conmpany_interview_list_api?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(CompanyGetInterviewList::class.java)

        retrofit.getJobHome().enqueue(
            object : Callback<conmpany_interview_list_api> {
                override fun onFailure(call: Call<conmpany_interview_list_api>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<conmpany_interview_list_api>, response: Response<conmpany_interview_list_api>) {
                    onResult(response.body())
                }
            }
        )
    }


    interface UploadChatPhoto {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("chat/uploadPhoto")
        fun UploadChatPhoto(@Part photo : MultipartBody.Part): Call<returnUploadChatPhotoApi>
    }
    fun UploadChatPhoto(context: Context?, photo : File, onResult: (returnUploadChatPhotoApi?) -> Unit){
        val retrofit = ServiceBuilder(context).POSTFILE(UploadChatPhoto::class.java)

        retrofit.UploadChatPhoto(MultipartBody.Part.createFormData("photo", photo.name)).enqueue(
            object : Callback<returnUploadChatPhotoApi> {
                override fun onFailure(call: Call<returnUploadChatPhotoApi>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<returnUploadChatPhotoApi>, response: Response<returnUploadChatPhotoApi>) {
                    onResult(response.body())
                }
            }
        )
    }

}
