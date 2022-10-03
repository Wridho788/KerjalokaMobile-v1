package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
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
    fun getCompanyJobOfficer(context: Context?, onResult: (getJobResponse?) -> Unit){
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
              }
          }
      )
    }
}