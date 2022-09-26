package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.company_detail_model
import com.ciptakerjaarunika.kerjaloka.model.Data.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class DataAPI {
    data class skillResponse(
        val code : Int,
        val data : List <Skill>
    )
    interface skill {
        @GET("data/skill/search")
        fun getData(@Query("keywords") keywords: String): Call<skillResponse>
    }

    fun GetSkills(
        context: Context?,
        keywords: String,
        onResult: (skillResponse?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(skill::class.java)

            retrofit.getData(keywords).enqueue(
                object : Callback<skillResponse> {
                    override fun onResponse(
                        call: Call<skillResponse>,
                        response: Response<skillResponse>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<skillResponse>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

    //Get Major
    data class majorResponse(
        val code : Int,
        val data : List <Major>
    )
    interface major {
        @GET("data/majors")
        fun getData(): Call<majorResponse>
    }

    fun GetMajors(
        context: Context?,
        onResult: (majorResponse?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(major::class.java)

            retrofit.getData().enqueue(
                object : Callback<majorResponse> {
                    override fun onResponse(
                        call: Call<majorResponse>,
                        response: Response<majorResponse>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<majorResponse>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

    //Get Titles
    data class titleResponse(
        val code : Int,
        val data : List <Title>
    )
    interface title {
        @GET("data/titles")
        fun getData(): Call<titleResponse>
    }

    fun GetTitles(
        context: Context?,
        onResult: (titleResponse?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(title::class.java)

            retrofit.getData().enqueue(
                object : Callback<titleResponse> {
                    override fun onResponse(
                        call: Call<titleResponse>,
                        response: Response<titleResponse>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<titleResponse>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }


    interface GetLocations {
        @GET("data/location")
        fun GetData(): Call<List<LocationFilter>?>
    }

    fun GetLocations(context: Context?, onResult: (List<LocationFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetLocations::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<LocationFilter>?> {
                override fun onResponse(call: Call<List<LocationFilter>?>, response: Response<List<LocationFilter>?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<LocationFilter>?>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    interface GetJobTypes {
        @GET("data/job-type")
        fun GetData(): Call<List<JobTypeFilter>>
    }

    fun GetJobTypes(context: Context?, onResult: (List<JobTypeFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetJobTypes::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<JobTypeFilter>> {
                override fun onResponse(call: Call<List<JobTypeFilter>>, response: Response<List<JobTypeFilter>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<JobTypeFilter>>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    interface GetSkills {
        @GET("data/skills")
        fun GetData(): Call<List<SkillFilter>?>
    }

    fun GetSkill(context: Context?, onResult: (List<SkillFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetSkills::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<SkillFilter>?> {
                override fun onResponse(call: Call<List<SkillFilter>?>, response: Response<List<SkillFilter>?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<SkillFilter>?>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }


    interface GetExperienceLevel {
        @GET("data/experience-level")
        fun GetData(): Call<List<ExperienceLevelFilter>?>
    }

    fun GetExperienceLevel(context: Context?, onResult: (List<ExperienceLevelFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetExperienceLevel::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<ExperienceLevelFilter>?> {
                override fun onResponse(call: Call<List<ExperienceLevelFilter>?>, response: Response<List<ExperienceLevelFilter>?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<ExperienceLevelFilter>?>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}