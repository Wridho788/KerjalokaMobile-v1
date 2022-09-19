package com.ciptakerjaarunika.kerjaloka.api


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.model.Interview.conmpany_interview_list_api
import com.ciptakerjaarunika.kerjaloka.model.Interview.jobseeker_interview_list_api
import com.ciptakerjaarunika.kerjaloka.model.Interview.returnUploadChatPhotoApi
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*


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

    interface JobseekerGetInterviewList {
        @GET("jobseeker/getinterview")
        fun getInterviewList(): Call<jobseeker_interview_list_api>
    }
    fun JobseekerGetInterviewList(context: Context?, mainActivity: MainActivity, onResult: (jobseeker_interview_list_api?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(JobseekerGetInterviewList::class.java)

        retrofit.getInterviewList().enqueue(
            object : Callback<jobseeker_interview_list_api> {
                override fun onFailure(call: Call<jobseeker_interview_list_api>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<jobseeker_interview_list_api>, response: Response<jobseeker_interview_list_api>) {
                    Log.d("Response Code : ", response.code().toString())
                    if(response.code() == 401){
                        mainActivity.showLogin(InterviewPage())
                    }else {
                        onResult(response.body())
                    }
                }
            }
        )
    }


    interface UploadChatPhoto {
        @Multipart
        @POST("users/chat/uploadPhoto")
        fun UploadChatPhoto(@Part photo : MultipartBody.Part): Call<returnUploadChatPhotoApi>
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun UploadChatPhoto(context: Context?, photo : MultipartBody.Part, onResult: (returnUploadChatPhotoApi?) -> Unit){
        val retrofit = ServiceBuilder(context).POSTFILE(UploadChatPhoto::class.java)
            retrofit.UploadChatPhoto(photo).enqueue(
                object : Callback<returnUploadChatPhotoApi> {
                    override fun onFailure(call: Call<returnUploadChatPhotoApi>, t: Throwable) {
                        Log.d("error", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<returnUploadChatPhotoApi>,
                        response: Response<returnUploadChatPhotoApi>
                    ) {
                        onResult(response.body())
                    }
                }
            )
    }

    interface UploadChatFile {
        @Multipart
        @POST("users/chat/uploadFile")
        fun UploadChatFile(@Part photo : MultipartBody.Part): Call<returnUploadChatPhotoApi>
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun UploadChatFile(context: Context?, photo : MultipartBody.Part, onResult: (returnUploadChatPhotoApi?) -> Unit){
        val retrofit = ServiceBuilder(context).POSTFILE(UploadChatFile::class.java)
        retrofit.UploadChatFile(photo).enqueue(
            object : Callback<returnUploadChatPhotoApi> {
                override fun onFailure(call: Call<returnUploadChatPhotoApi>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<returnUploadChatPhotoApi>,
                    response: Response<returnUploadChatPhotoApi>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

}
