package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.company_detail_model
import com.ciptakerjaarunika.kerjaloka.model.Data.Major
import com.ciptakerjaarunika.kerjaloka.model.Data.Skill
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
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
}