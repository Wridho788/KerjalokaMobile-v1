package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.model.Data.JobTypeFilter
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class JobTypes {

    interface GetJobTypes {
        @GET("data/job-type")
        fun GetData(): Call<List<JobTypeFilter>>
    }

    fun GetJobTypes(context: Context?, onResult: (List<JobTypeFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetJobTypes::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<JobTypeFilter>> {
                override fun onResponse(
                    call: Call<List<JobTypeFilter>>, response: Response<List<JobTypeFilter>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<JobTypeFilter>>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

}