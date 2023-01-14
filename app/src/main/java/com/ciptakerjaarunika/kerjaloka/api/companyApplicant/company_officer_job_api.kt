package com.ciptakerjaarunika.kerjaloka.api.companyApplicant

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.listApplicantResponse
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ListApplicant.Model.company_officer_jobs_response
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ListApplicant.Model.cvBank_response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class CompanyOfficerJobsApi {
    interface getOfficerJobs {
        @GET("/company/officer/jobs/own")
        fun getOfficerJobs(): Call<company_officer_jobs_response>
    }

    interface getCVBanks {
        @GET("/company/officer/application/cvbank/total")
        fun getCVBanks(): Call<cvBank_response>
    }

    interface getCVBanksList {
        @GET("/company/officer/application/cvbank")
        fun getCVBanksList(): Call<listApplicantResponse>
    }

    fun CompanyOfficerJob(context: Context?, onResult: (company_officer_jobs_response?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(getOfficerJobs::class.java)

        retrofit.getOfficerJobs().enqueue(
            object : Callback<company_officer_jobs_response>{
                override fun onResponse(
                    call: Call<company_officer_jobs_response>,
                    response: Response<company_officer_jobs_response>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<company_officer_jobs_response>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    fun GetCVBanks(context: Context?, onResult: (cvBank_response?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(getCVBanks::class.java)
        retrofit.getCVBanks().enqueue(
            object : Callback<cvBank_response> {
                override fun onResponse(
                    call: Call<cvBank_response>,
                    response: Response<cvBank_response>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<cvBank_response>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    fun GetCVBanksList(context: Context?, onResult: (listApplicantResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getCVBanksList::class.java)
        retrofit.getCVBanksList().enqueue(
            object : Callback<listApplicantResponse>{
                override fun onResponse(
                    call: Call<listApplicantResponse>,
                    response: Response<listApplicantResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<listApplicantResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}