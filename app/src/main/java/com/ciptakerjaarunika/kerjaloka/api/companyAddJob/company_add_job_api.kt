package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.addJobRequest
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.addJobResponse
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class AddJobAPI {
    interface IaddJob {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("company/officer/job/add")
        fun addJob(@Body addJobRequest: addJobRequest): Call<addJobResponse>
    }
    fun AddJob(context: Context?, addJobRequest: addJobRequest, onResult: (addJobResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(IaddJob::class.java)
        retrofit.addJob(addJobRequest).enqueue(
            object : Callback<addJobResponse>{
                override fun onResponse(
                    call: Call<addJobResponse>,
                    response: Response<addJobResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<addJobResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}