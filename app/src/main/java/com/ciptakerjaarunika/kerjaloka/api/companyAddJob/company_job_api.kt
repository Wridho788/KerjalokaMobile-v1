package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.getJobResponse
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class CompanyJobAPI {
    interface getCompanyJobOfficer {
        @GET("company/officer/jobs")
        fun getCompanyJob(): Call<getJobResponse>
    }

    fun getCompanyJobOfficer(context: Context?, onResult: (getJobResponse?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(getCompanyJobOfficer::class.java)
        retrofit.getCompanyJob().enqueue(
            object : Callback<getJobResponse> {
                override fun onResponse(
                    call: Call<getJobResponse>,
                    response: Response<getJobResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<getJobResponse>, t: Throwable) {
                    onResult(null)
                    Log.d("response fail", t.toString())

                }
            }
        )
    }

    data class CompanyAnalytic(
        val code: Int,
        val message: String,
        val data: itemAnalytic
    )

    data class itemAnalytic(
        val viewCount: Int,
    )

    interface getCompanyAnalytic {
        @GET("users/analytic/get")
        fun getCompanyAnalytic(): Call<CompanyAnalytic>
    }
    fun GetCompanyAnalytic(context: Context?, analyticItemType: Int, jobNo: Long, onResult: (CompanyAnalytic?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(getCompanyAnalytic::class.java)
        retrofit.getCompanyAnalytic().enqueue(
            object : Callback<CompanyAnalytic> {
                override fun onResponse(
                    call: Call<CompanyAnalytic>,
                    response: Response<CompanyAnalytic>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CompanyAnalytic>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}