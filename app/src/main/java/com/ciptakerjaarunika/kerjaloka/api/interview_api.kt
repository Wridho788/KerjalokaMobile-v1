package com.ciptakerjaarunika.kerjaloka.api


import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.model.Interview.conmpany_interview_list_api
import com.ciptakerjaarunika.kerjaloka.model.Interview.jobseeker_interview_list_api
import com.ciptakerjaarunika.kerjaloka.model.Interview.returnUploadChatPhotoApi
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.lang.System.out

import java.nio.file.Files
import java.nio.file.Paths


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
                        mainActivity.showLogin()
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
        /*val file = Bitmap.CompressFormat.PNG .compress(Bitmap.CompressFormat.PNG, quality, outStream);
        if (out.flush() != null) {
            val file_path = Environment.getExternalStorageDirectory().absolutePath +
                    "/Kerjaloka/sendImage"
            val dir = File(file_path)
            if (!dir.exists()) {
                Files.createDirectories(Paths.get(file_path))
                dir.mkdir()
            }

            val file = File(file_path + "/"+ "photo" + photo.generationId.toString() + ".png")
            val fw = FileWriter(file.absoluteFile)
            val bw = BufferedWriter(fw)
            bw.write(photo.toString())
            bw.close()

            val fOut = FileOutputStream(file)

            photo.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            fOut.close()

            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
//        val requestFile = RequestBody.create("image/x".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)

        */
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

}
