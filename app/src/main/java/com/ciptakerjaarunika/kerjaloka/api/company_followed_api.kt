package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_followed_job_model
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class CompanyFollowedAPI {
    interface CompanyFollowedAPIList {
        @GET("/jobseeker/following")
        fun getFollowedJob(): Call<company_followed_job_model>
    }
    fun CompanyGetFollowedJob(context: Context?, onResult: (company_followed_job_model?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(CompanyFollowedAPIList::class.java)

        retrofit.getFollowedJob().enqueue(
            object : Callback<company_followed_job_model>{
                override fun onResponse(
                    call: Call<company_followed_job_model>,
                    response: Response<company_followed_job_model>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<company_followed_job_model>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
            }
        )
    }
}