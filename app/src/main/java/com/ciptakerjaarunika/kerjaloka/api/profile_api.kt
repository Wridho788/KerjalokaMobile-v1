package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Profile.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.search_model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

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
}