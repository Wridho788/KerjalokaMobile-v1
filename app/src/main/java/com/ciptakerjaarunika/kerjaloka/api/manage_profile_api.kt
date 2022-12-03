package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ciptakerjaarunika.kerjaloka.model.Data.CheckDocument
import com.ciptakerjaarunika.kerjaloka.model.Data.Documents
import com.ciptakerjaarunika.kerjaloka.model.Profile.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

class ManageProfileAPI {
    data class editAboutMeRequest(
        val jobseekerAbout: String
    )

    interface editAboutMe {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/edit/about")
        fun sendData(@Body newData: editAboutMeRequest): Call<Any>
    }

    fun EditAboutMe(aboutMe: String, context: Context?, onResult: (Any?) -> Unit) {
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
        val maritalNo: Int?,
        val religionNo: Int?,
        val postalCode: String?,
        val placeOfBirth: String?,
        val ethnics: String?,
        val residentNo: Int?,
        val telegramId: String?,
        val instagramId: String?,
    )

    interface editAdditional {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/edit/additional")
        fun sendData(@Body newData: editAdditionalRequest): Call<Any>
    }

    fun EditAdditional(data: editAdditionalRequest, context: Context?, onResult: (Any?) -> Unit) {
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
        val name: String?,
        val ktp: String?,
        val gender: Char?,
        val address: String?,
        val dateOfBirth: String?,
        val cityNo: Int?,
    )

    interface editBasicInfo {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/edit/basic")
        fun sendData(@Body newData: editBasicInfoRequest): Call<Any>
    }

    fun EditBasicInfo(data: editBasicInfoRequest, context: Context?, onResult: (Any?) -> Unit) {
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
        val code: Int,
        val message: String,
        val data: String?
    )

    interface UploadPhoto {
        @Multipart
        @POST("jobseeker/mobile/editphoto")
        fun UploadPhoto(@Part photo: MultipartBody.Part): Call<jobseekerUploadPhotoResponse>
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun UploadPhoto(
        context: Context?,
        photo: MultipartBody.Part,
        onResult: (jobseekerUploadPhotoResponse?) -> Unit
    ) {
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
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/languages/manage")
        fun SendData(@Body jobseekerLanguages: List<JobseekerLanguages>?): Call<Any?>
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun JobseekerEditLanguages(
        jobseekerLanguages: List<JobseekerLanguages>?,
        context: Context?,
        onResult: (Any?) -> Unit
    ) {
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
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/preference/field/manage")
        fun SendData(@Body jobseekerFields: List<JobseekerFields>?): Call<Any?>
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun JobseekerEditFields(
        jobseekerFields: List<JobseekerFields>?,
        context: Context?,
        onResult: (Any?) -> Unit
    ) {
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
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/skills/manage")
        fun SendData(@Body jobseekerLanguages: List<JobseekerSkills>?): Call<Any?>
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun JobseekerEditSkills(
        jobseekerSkills: List<JobseekerSkills>?,
        context: Context?,
        onResult: (Any?) -> Unit
    ) {
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
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/preference/job-type/manage")
        fun SendData(@Body jobseekerJobType: List<JobseekerJobTypes>?): Call<Any?>
    }

    fun JobseekerEditJobTypes(
        jobseekerJobType: List<JobseekerJobTypes>?,
        context: Context?,
        onResult: (Any?) -> Unit
    ) {
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
        val expectedSalary: Int?
    )

    interface EditSalaryExpectation {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/preference/salary/manage")
        fun SendData(@Body salaryExpectation: SalaryExpectationRequest?): Call<Any?>
    }

    fun EditSalaryExpectation(salary: Int, context: Context?, onResult: (Any?) -> Unit) {
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
        val code: Int,
        val message: String,
        val documentName: String?
    )

    interface UploadDocument {
        @Multipart
        @POST("users/mobile/uploadDocument")
        fun SendData(@Part document: MultipartBody.Part): Call<UploadDocumentResponse>
    }

    fun JobseekerUploadDocument(
        context: Context?,
        document: MultipartBody.Part,
        onResult: (UploadDocumentResponse?) -> Unit
    ) {
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
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/documents")
        fun SendData(@Body documents: List<Documents>?): Call<Any?>
    }

    fun JobseekerEditLampiran(docs: List<Documents>?, context: Context?, onResult: (Any?) -> Unit) {
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
        val username: String
    )

    data class responseGeneral(
        val code: Any,
        val message: String,
        val data: Any?
    )

    data class responseGeneralCodeInt(
        val code: Int,
        val message: String,
        val data: Any?
    )

    interface JobseekerChangeUsername {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("users/change/username")
        fun SendData(@Body newUsername: changeUsername): Call<responseGeneral?>
    }

    fun JobseekerChangeUsername(
        username: String,
        context: Context?,
        onResult: (responseGeneral?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).POST(JobseekerChangeUsername::class.java)
        retrofit.SendData(changeUsername(username)).enqueue(
            object : Callback<responseGeneral?> {
                override fun onFailure(call: Call<responseGeneral?>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<responseGeneral?>,
                    response: Response<responseGeneral?>
                ) {
                    if (response.body() != null) {
                        onResult(response.body())
                    } else {
                        val data: String = response.errorBody()!!.string()
                        try {
                            val jObjError = JSONObject(data)
                            val map = jObjError.getString("message")
                            Toast.makeText(
                                context,
                                "Username baru saja diganti, dan dapat diganti kembali jika sudah 30 hari",
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }

                        Log.d("response", response.toString())
                    }
                }
            }
        )
    }

    interface JobseekerDeleteExperience {
        @GET("jobseeker/experience/{experienceNo}/delete")
        fun SendData(@Path("experienceNo") experienceNo: Long): Call<responseGeneral?>
    }

    fun JobseekerDeleteExperience(
        experienceNo: Long,
        context: Context?,
        onResult: (responseGeneral?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).GET(JobseekerDeleteExperience::class.java)
        retrofit.SendData(experienceNo).enqueue(
            object : Callback<responseGeneral?> {
                override fun onFailure(call: Call<responseGeneral?>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<responseGeneral?>,
                    response: Response<responseGeneral?>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    interface JobseekerAddExperience {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/experience/add")
        fun SendData(@Body files: JobseekerExperienceRequest): Call<responseGeneral?>
    }

    interface JobseekerEditExperience {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/experience/{experienceNo}/edit")
        fun SendData(
            @Body files: JobseekerExperienceRequest,
            @Path("experienceNo") experienceNo: Long
        ): Call<responseGeneral?>
    }

    fun JobseekerManageExperience(
        experiences: JobseekerExperienceRequest,
        context: Context?,
        onResult: (responseGeneral?) -> Unit
    ) {
        if (experiences.jobseekerExperienceNo != null && experiences.jobseekerExperienceNo != 0L) {
            val retrofit = ServiceBuilder(context).POST(JobseekerEditExperience::class.java)
            retrofit.SendData(experiences, experiences.jobseekerExperienceNo).enqueue(
                object : Callback<responseGeneral?> {
                    override fun onFailure(call: Call<responseGeneral?>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<responseGeneral?>,
                        response: Response<responseGeneral?>
                    ) {
                        onResult(response.body())
                    }
                }
            )
        } else {
            val retrofit = ServiceBuilder(context).POST(JobseekerAddExperience::class.java)
            retrofit.SendData(experiences).enqueue(
                object : Callback<responseGeneral?> {
                    override fun onFailure(call: Call<responseGeneral?>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<responseGeneral?>,
                        response: Response<responseGeneral?>
                    ) {
                        onResult(response.body())
                    }
                }
            )
        }
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
    interface JobseekerDeleteEducation {
        @GET("jobseeker/education/{educationNo}/delete")
        fun SendData(@Path("educationNo") educationNo: Long): Call<responseGeneral?>
    }

    fun JobseekerDeleteEducation(
        educationNo: Long,
        context: Context?,
        onResult: (responseGeneral?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).GET(JobseekerDeleteEducation::class.java)
        retrofit.SendData(educationNo).enqueue(
            object : Callback<responseGeneral?> {
                override fun onFailure(call: Call<responseGeneral?>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<responseGeneral?>,
                    response: Response<responseGeneral?>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    interface JobseekerAddEdication {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/education/add")
        fun SendData(@Body files: JobseekerEducationsRequest): Call<responseGeneral?>
    }

    interface JobseekerEditEducation {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/education/{educationNo}/edit")
        fun SendData(
            @Body files: JobseekerEducationsRequest,
            @Path("educationNo") educationNo: Long
        ): Call<responseGeneral?>
    }

    fun JobseekerManageEducation(
        education: JobseekerEducationsRequest,
        context: Context?,
        onResult: (responseGeneral?) -> Unit
    ) {
        if (education.jobseekerEducationNo != null && education.jobseekerEducationNo != 0L) {
            val retrofit = ServiceBuilder(context).POST(JobseekerEditEducation::class.java)
            retrofit.SendData(education, education.jobseekerEducationNo).enqueue(
                object : Callback<responseGeneral?> {
                    override fun onFailure(call: Call<responseGeneral?>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<responseGeneral?>,
                        response: Response<responseGeneral?>
                    ) {
                        onResult(response.body())
                    }
                }
            )
        } else {
            val retrofit = ServiceBuilder(context).POST(JobseekerAddEdication::class.java)
            retrofit.SendData(education).enqueue(
                object : Callback<responseGeneral?> {
                    override fun onFailure(call: Call<responseGeneral?>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<responseGeneral?>,
                        response: Response<responseGeneral?>
                    ) {
                        onResult(response.body())
                    }
                }
            )
        }
    }

    interface JobseekerDeleteVaccine {
        @GET("jobseeker/vaccineCertificate/delete")
        fun SendData(@Query("vaccine") vaccine: Int): Call<responseGeneral?>
    }

    fun JobseekerDeleteVaccine(
        vaccine: Int,
        context: Context?,
        onResult: (responseGeneral?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).GET(JobseekerDeleteVaccine::class.java)
        retrofit.SendData(vaccine).enqueue(
            object : Callback<responseGeneral?> {
                override fun onFailure(call: Call<responseGeneral?>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<responseGeneral?>,
                    response: Response<responseGeneral?>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    data class uploadVaccineResponse(
        val code: Int,
        val message: String,
        val data: CheckDocument?
    )

    interface UploadVaccine {
        @Multipart
        @POST("jobseeker/mobile/uploadvaccine/{vaccine}")
        fun SenData(
            @Path("vaccine") vaccine: Int,
            @Part file: MultipartBody.Part
        ): Call<uploadVaccineResponse>
    }

    fun UploadVaccine(
        vaccine: Int,
        file: MultipartBody.Part,
        context: Context?,
        onResult: (uploadVaccineResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).POSTFILE(UploadVaccine::class.java)
        retrofit.SenData(vaccine, file).enqueue(
            object : Callback<uploadVaccineResponse> {
                override fun onFailure(call: Call<uploadVaccineResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<uploadVaccineResponse>,
                    response: Response<uploadVaccineResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    data class uploadVideoResumeResponse(
        val code: Int,
        val message: String,
        val data: JobseekerVideoResume?
    )

    interface UploadVideoResume {
        @Multipart
        @POST("jobseeker/mobile/uploadresume")
        fun SenData(@Part files: MultipartBody.Part): Call<uploadVideoResumeResponse>
    }

    fun UploadVideoResume(
        files: MultipartBody.Part,
        context: Context?,
        onResult: (uploadVideoResumeResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).POSTFILE(UploadVideoResume::class.java)
        retrofit.SenData(files).enqueue(
            object : Callback<uploadVideoResumeResponse> {
                override fun onFailure(call: Call<uploadVideoResumeResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<uploadVideoResumeResponse>,
                    response: Response<uploadVideoResumeResponse>
                ) {
                    if (response.body() != null) {
                        onResult(response.body())
                    } else {
                        val data: String = response.errorBody()!!.string()
                        try {
                            val jObjError = JSONObject(data)
                            val map = jObjError.getString("message")
                            Toast.makeText(
                                context,
                                map,
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }

                        Log.d("response", response.toString())
                    }
                }
            }
        )
    }

    data class SendAppealRecordRequest(
        val recordNo: Int,
        val description: String,
    )

    interface sendAppealRecord {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/record/appeal/send")
        fun sendData(@Body reqData: SendAppealRecordRequest): Call<responseGeneralCodeInt?>
    }

    fun SendAppealRecord(
        recordNo: Int,
        description: String,
        context: Context?,
        onResult: (responseGeneralCodeInt?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).POST(sendAppealRecord::class.java)

        retrofit.sendData(SendAppealRecordRequest(recordNo, description)).enqueue(
            object : Callback<responseGeneralCodeInt?> {
                override fun onResponse(
                    call: Call<responseGeneralCodeInt?>,
                    response: Response<responseGeneralCodeInt?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<responseGeneralCodeInt?>, t: Throwable) {
                    Log.e("asd", t.toString())
                    onResult(null)
                }

            }
        )
    }
}