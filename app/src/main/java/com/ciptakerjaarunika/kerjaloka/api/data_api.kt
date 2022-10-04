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
    interface major {
        @GET("data/majors")
        fun getData(): Call<List <Major>?>
    }

    fun GetMajors(
        context: Context?,
        onResult: (List <Major>?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(major::class.java)

            retrofit.getData().enqueue(
                object : Callback<List<Major>?> {
                    override fun onResponse(
                        call: Call<List<Major>?>,
                        response: Response<List<Major>?>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<List<Major>?>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

    //Get Maritals
    interface marital {
        @GET("data/maritals")
        fun getData(): Call<List<Marital>?>
    }

    fun GetMaritals(
        context: Context?,
        onResult: (List<Marital>?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(marital::class.java)

            retrofit.getData().enqueue(
                object : Callback<List<Marital>?> {
                    override fun onResponse(
                        call: Call<List<Marital>?>,
                        response: Response<List<Marital>?>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<List<Marital>?>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

    //Get Religions
    interface religion {
        @GET("data/religions")
        fun getData(): Call<List<Religion>?>
    }

    fun GetReligions(
        context: Context?,
        onResult: (List<Religion>?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(religion::class.java)

            retrofit.getData().enqueue(
                object : Callback<List<Religion>?> {
                    override fun onResponse(
                        call: Call<List<Religion>?>,
                        response: Response<List<Religion>?>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<List<Religion>?>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

    //Get Residents
    interface residents {
        @GET("data/residents")
        fun getData(): Call<List<Resident>?>
    }

    fun GetResidents(
        context: Context?,
        onResult: (List<Resident>?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(residents::class.java)

            retrofit.getData().enqueue(
                object : Callback<List<Resident>?> {
                    override fun onResponse(
                        call: Call<List<Resident>?>,
                        response: Response<List<Resident>?>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<List<Resident>?>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

    //Get Titles
    interface title {
        @GET("data/titles")
        fun getData(): Call<List <Title>?>
    }

    fun GetTitles(
        context: Context?,
        onResult: (List <Title>?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(title::class.java)

            retrofit.getData().enqueue(
                object : Callback<List <Title>?> {
                    override fun onResponse(
                        call: Call<List <Title>?>,
                        response: Response<List <Title>?>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<List <Title>?>, t: Throwable) {
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

    interface GetLanguages {
        @GET("data/language")
        fun GetData(): Call<List<Language>?>
    }

    fun GetLanguages(context: Context?, onResult: (List<Language>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetLanguages::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<Language>?> {
                override fun onResponse(call: Call<List<Language>?>, response: Response<List<Language>?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<Language>?>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
    interface GetFields {
        @GET("data/fields")
        fun GetData(): Call<List<FieldFilter>?>
    }

    fun GetFields(context: Context?, onResult: (List<FieldFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetFields::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<FieldFilter>?> {
                override fun onResponse(call: Call<List<FieldFilter>?>, response: Response<List<FieldFilter>?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<FieldFilter>?>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}