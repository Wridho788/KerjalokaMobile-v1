package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.model.Data.ExperienceLevelFilter
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class ExperienceLevels {
    interface GetExperienceLevel {
        @GET("data/experience-level")
        fun GetData(): Call<List<ExperienceLevelFilter>?>
    }

    fun GetExperienceLevel(context: Context?, onResult: (List<ExperienceLevelFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetExperienceLevel::class.java)

        retrofit.GetData().enqueue(object : Callback<List<ExperienceLevelFilter>?> {
            override fun onResponse(
                call: Call<List<ExperienceLevelFilter>?>,
                response: Response<List<ExperienceLevelFilter>?>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<List<ExperienceLevelFilter>?>, t: Throwable) {
                onResult(null)
            }
        })
    }

}