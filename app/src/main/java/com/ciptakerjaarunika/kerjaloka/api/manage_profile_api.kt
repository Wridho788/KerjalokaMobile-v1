package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ciptakerjaarunika.kerjaloka.model.Data.Documents
import com.ciptakerjaarunika.kerjaloka.model.Interview.returnUploadChatPhotoApi
import com.ciptakerjaarunika.kerjaloka.model.Profile.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.user
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference.FragmentSalaryExpectation
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


    interface JobseekerEditLanguage {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/languages/manage")
        fun SendData(@Body jobseekerLanguages : List<JobseekerLanguages>?): Call<Any?>
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun JobseekerEditLanguages(jobseekerLanguages: List<JobseekerLanguages>?, context: Context?, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(JobseekerEditLanguage::class.java)
        retrofit.SendData(jobseekerLanguages).enqueue(
            object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Any?>,
                    response: Response<Any?>
                ) {
                    onResult(response.body())
                }
            }
        )
    }


    interface JobseekerEditFields {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/preference/field/manage")
        fun SendData(@Body jobseekerFields : List<JobseekerFields>?): Call<Any?>
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun JobseekerEditFields(jobseekerFields: List<JobseekerFields>?, context: Context?, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(JobseekerEditFields::class.java)
        retrofit.SendData(jobseekerFields).enqueue(
            object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Any?>,
                    response: Response<Any?>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    interface JobseekerEditSkills {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/skills/manage")
        fun SendData(@Body jobseekerLanguages : List<JobseekerSkills>?): Call<Any?>
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun JobseekerEditSkills(jobseekerSkills: List<JobseekerSkills>?, context: Context?, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(JobseekerEditSkills::class.java)
        retrofit.SendData(jobseekerSkills).enqueue(
            object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Any?>,
                    response: Response<Any?>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    interface JobseekerEditJobTypes {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/preference/job-type/manage")
        fun SendData(@Body jobseekerJobType: List<JobseekerJobTypes>?): Call<Any?>
    }
    fun JobseekerEditJobTypes(jobseekerJobType: List<JobseekerJobTypes>?, context: Context?, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(JobseekerEditJobTypes::class.java)
        retrofit.SendData(jobseekerJobType).enqueue(
            object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                    onResult(response.body())
                }
            }
        )
    }

    data class SalaryExpectationRequest(
        val expectedSalary : Int?
    )
    interface EditSalaryExpectation {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/preference/salary/manage")
        fun SendData(@Body salaryExpectation: SalaryExpectationRequest?): Call<Any?>
    }
    fun EditSalaryExpectation(salary : Int?, context: Context?, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(EditSalaryExpectation::class.java)
        retrofit.SendData(SalaryExpectationRequest(salary)).enqueue(
            object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                    onResult(response.body())
                }
            }
        )
    }

    data class UploadDocumentResponse(
        val code : Int,
        val message:String,
        val documentName: String?
    )
    interface UploadDocument {
        @Multipart
        @POST("users/mobile/uploadDocument")
        fun SendData(@Part document : MultipartBody.Part): Call<UploadDocumentResponse>
    }

    fun JobseekerUploadDocument(context: Context?, document : MultipartBody.Part, onResult: (UploadDocumentResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POSTFILE(UploadDocument::class.java)
        retrofit.SendData(document).enqueue(
            object : Callback<UploadDocumentResponse> {
                override fun onFailure(call: Call<UploadDocumentResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<UploadDocumentResponse>,
                    response: Response<UploadDocumentResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }
    interface JobseekerEditLampiran {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("jobseeker/documents")
        fun SendData(@Body documents: List<Documents>?): Call<Any?>
    }
    fun JobseekerEditLampiran(docs: List<Documents>?, context: Context?, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(JobseekerEditLampiran::class.java)
        retrofit.SendData(docs).enqueue(
            object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                    onResult(response.body())
                }
            }
        )
    }

    data class changeUsername(
        val username:String
    )
    interface JobseekerChangeUsername {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("users/change/username")
        fun SendData(@Body newUsername: changeUsername): Call<Any?>
    }
    fun JobseekerChnageUsername(username : String, context: Context?, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(JobseekerChangeUsername::class.java)
        retrofit.SendData(changeUsername(username)).enqueue(
            object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                    onResult(response.body())
                }
            }
        )
    }


//    interface GetMyReview {
//        @Get("jobseeker/rating/myReview")
//        fun GetData(): Call<Any?>
//    }
//    fun JobseekerChnageUsername(username : String, context: Context?, onResult: (Any?) -> Unit){
//        val retrofit = ServiceBuilder(context).POST(JobseekerChangeUsername::class.java)
//        retrofit.SendData(changeUsername(username)).enqueue(
//            object : Callback<Any?> {
//                override fun onFailure(call: Call<Any?>, t: Throwable) {
//                    onResult(null)
//                }
//                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
//                    onResult(response.body())
//                }
//            }
//        )
//    }
}