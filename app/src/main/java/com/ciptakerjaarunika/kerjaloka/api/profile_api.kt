package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Data.CheckDocument
import com.ciptakerjaarunika.kerjaloka.model.Data.Documents
import com.ciptakerjaarunika.kerjaloka.model.Data.Field
import com.ciptakerjaarunika.kerjaloka.model.Data.JobType
import com.ciptakerjaarunika.kerjaloka.model.Profile.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.review_response
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.search_model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal

class ProfileAPI {

    interface jobseekerGetProfileData {
        @GET("jobseeker")
        fun getProfileData(): Call<JobseekerProfileResponse>
    }

    fun JobseekerGetProfileData(context: Context?,onResult: (JobseekerProfileResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerGetProfileData::class.java)

        retrofit.getProfileData().enqueue(
            object : Callback<JobseekerProfileResponse> {
                override fun onFailure(call: Call<JobseekerProfileResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerProfileResponse>, response: Response<JobseekerProfileResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }


    //Get Jobseeker Skills
    interface jobseekerSkills {
        @GET("jobseeker/skills")
        fun getJobseekerSkills(): Call<JobseekerSkillsResponse>
    }

    fun GetJobseekerSkills(context: Context?,onResult: (JobseekerSkillsResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerSkills::class.java)

        retrofit.getJobseekerSkills().enqueue(
            object : Callback<JobseekerSkillsResponse> {
                override fun onFailure(call: Call<JobseekerSkillsResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerSkillsResponse>, response: Response<JobseekerSkillsResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    //Get Jobseeker Educations
    interface jobseekerEducations {
        @GET("jobseeker/educations")
        fun getJobseekerEducations(): Call<JobseekerEducationsResponse>
    }

    fun GetJobseekerEducations(context: Context?,onResult: (JobseekerEducationsResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerEducations::class.java)

        retrofit.getJobseekerEducations().enqueue(
            object : Callback<JobseekerEducationsResponse> {
                override fun onFailure(call: Call<JobseekerEducationsResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerEducationsResponse>, response: Response<JobseekerEducationsResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    //Get Jobseeker Experiences
    interface jobseekerExperiences {
        @GET("jobseeker/experiences")
        fun getJobseekerExperiences(): Call<JobseekerExperiencesResponse>
    }

    fun GetJobseekerExperiences(context: Context?,onResult: (JobseekerExperiencesResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerExperiences::class.java)

        retrofit.getJobseekerExperiences().enqueue(
            object : Callback<JobseekerExperiencesResponse> {
                override fun onFailure(call: Call<JobseekerExperiencesResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerExperiencesResponse>, response: Response<JobseekerExperiencesResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    //Get Jobseeker Languages
    interface jobseekerLanguages {
        @GET("jobseeker/languages")
        fun getJobseekerLanguages(): Call<JobseekerLanguagesResponse>
    }

    fun GetJobseekerLanguages(context: Context?,onResult: (JobseekerLanguagesResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerLanguages::class.java)

        retrofit.getJobseekerLanguages().enqueue(
            object : Callback<JobseekerLanguagesResponse> {
                override fun onFailure(call: Call<JobseekerLanguagesResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerLanguagesResponse>, response: Response<JobseekerLanguagesResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    //Jobseeker Get Expected Salary
    data class JobseekerSalaryExpectedResponse(
        val code : Int,
        val data : BigDecimal
    )
    interface jobseekerSalaryExpected {
        @GET("jobseeker/preference/salary")
        fun getJobseekerSalaryExpected(): Call<JobseekerSalaryExpectedResponse>
    }

    fun GetJobseekerSalaryExpected(context: Context?,onResult: (JobseekerSalaryExpectedResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerSalaryExpected::class.java)

        retrofit.getJobseekerSalaryExpected().enqueue(
            object : Callback<JobseekerSalaryExpectedResponse> {
                override fun onFailure(call: Call<JobseekerSalaryExpectedResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerSalaryExpectedResponse>, response: Response<JobseekerSalaryExpectedResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    //Get Jobseeker Job Type
    data class JobseekerJobTypeResponse(
        val code : Int,
        val data : List<JobType>
    )
    interface jobseekerJobType {
        @GET("jobseeker/preference/job-type")
        fun getJobseekerJobType(): Call<JobseekerJobTypeResponse>
    }

    fun GetJobseekerJobType(context: Context?,onResult: (JobseekerJobTypeResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerJobType::class.java)

        retrofit.getJobseekerJobType().enqueue(
            object : Callback<JobseekerJobTypeResponse> {
                override fun onFailure(call: Call<JobseekerJobTypeResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerJobTypeResponse>, response: Response<JobseekerJobTypeResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }
    //Get Jobseeker Job Type
    data class JobseekerFieldResponse(
        val code : Int,
        val data : List<Field>
    )
    interface jobseekerField {
        @GET("jobseeker/preference/field")
        fun getJobseekerField(): Call<JobseekerFieldResponse>
    }

    fun GetJobseekerField(context: Context?,onResult: (JobseekerFieldResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerField::class.java)

        retrofit.getJobseekerField().enqueue(
            object : Callback<JobseekerFieldResponse> {
                override fun onFailure(call: Call<JobseekerFieldResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerFieldResponse>, response: Response<JobseekerFieldResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    //Get Jobseeker Documents
    data class JobseekerDocumentsResponse(
        val code : Int,
        val data : List<Documents>
    )
    interface jobseekerDocuments {
        @GET("jobseeker/documents")
        fun getJobseekerDocuments(): Call<JobseekerDocumentsResponse>
    }

    fun GetJobseekerDocuments(context: Context?,onResult: (JobseekerDocumentsResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerDocuments::class.java)

        retrofit.getJobseekerDocuments().enqueue(
            object : Callback<JobseekerDocumentsResponse> {
                override fun onFailure(call: Call<JobseekerDocumentsResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerDocumentsResponse>, response: Response<JobseekerDocumentsResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    //Get Jobseeker Document Vaccine
    data class JobseekerDocumentVaccineResponse(
        val code : Int,
        val data : List<CheckDocument>
    )
    interface jobseekerDocumentVaccine {
        @GET("jobseeker/documents/vaccine")
        fun getJobseekerDocumentVaccine(): Call<JobseekerDocumentVaccineResponse>
    }

    fun GetJobseekerDocumentVaccine(context: Context?,onResult: (JobseekerDocumentVaccineResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerDocumentVaccine::class.java)

        retrofit.getJobseekerDocumentVaccine().enqueue(
            object : Callback<JobseekerDocumentVaccineResponse> {
                override fun onFailure(call: Call<JobseekerDocumentVaccineResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerDocumentVaccineResponse>, response: Response<JobseekerDocumentVaccineResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    //Get Jobseeker Get Resume
    data class JobseekerResumeResponse(
        val code : Int,
        val data : JobseekerVideoResume
    )
    interface jobseekerResume {
        @GET("jobseeker/get/resume")
        fun getJobseekerDocumentVaccine(): Call<JobseekerResumeResponse>
    }

    fun GetJobseekerResume(context: Context?,onResult: (JobseekerResumeResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerResume::class.java)

        retrofit.getJobseekerDocumentVaccine().enqueue(
            object : Callback<JobseekerResumeResponse> {
                override fun onFailure(call: Call<JobseekerResumeResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<JobseekerResumeResponse>, response: Response<JobseekerResumeResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    interface jobseekerDeleteResume {
        @GET("jobseeker/delete/resume")
        fun request(): Call<Any?>
    }

    fun DeleteJobseekerResume(context: Context?,onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerDeleteResume::class.java)

        retrofit.request().enqueue(
            object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                    onResult(response.body())
                }
            }
        )
    }

    data class recordResponse(
        val data : List<JobseekerRecord>
    )
    interface jobseekerGetRecord {
        @GET("jobseeker/record/get")
        fun request(): Call<recordResponse?>
    }

    fun JobseekerGetRecord(context: Context?,onResult: (recordResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerGetRecord::class.java)

        retrofit.request().enqueue(
            object : Callback<recordResponse?> {
                override fun onFailure(call: Call<recordResponse?>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<recordResponse?>, response: Response<recordResponse?>) {
                    onResult(response.body())
                }
            }
        )
    }

    interface jobseekerGetMyReview {
        @GET("jobseeker/rating/myReview")
        fun request(): Call<review_response?>
    }

    fun JobseekerGetMyReview(context: Context?,onResult: (review_response?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(jobseekerGetMyReview::class.java)

        retrofit.request().enqueue(
            object : Callback<review_response?> {
                override fun onFailure(call: Call<review_response?>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<review_response?>, response: Response<review_response?>) {
                    onResult(response.body())
                }
            }
        )
    }


    interface logout {
        @GET("users/logout")
        fun logout(): Call<Any>
    }

    fun Logout(context: Context?,onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(logout::class.java)

        retrofit.logout().enqueue(
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