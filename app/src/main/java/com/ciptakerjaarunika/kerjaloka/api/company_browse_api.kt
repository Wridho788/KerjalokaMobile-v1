package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.CompanyPage.company_browse_job_model
import com.ciptakerjaarunika.kerjaloka.model.User.Company
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class CompanyBrowseAPI {
    interface CompanyBrowserAPIList {
        @GET("/jobseeker/company/search")
        fun getBrowserJob(): Call<company_browse_job_model>
    }

    interface CompanyBrowserUnAuthorizedAPIList {
        @GET("/company/search/u")
        fun getBrowserJobAuthorized(): Call<company_browse_job_model>
    }

    fun CompanyGetBrowserJob(context: Context?, onResult: (company_browse_job_model?) -> Unit) {
        if (SessionManager(context).user == null) {
            val retrofitAuthorized =
                ServiceBuilder(context).GET(CompanyBrowserUnAuthorizedAPIList::class.java)
            retrofitAuthorized.getBrowserJobAuthorized().enqueue(
                object : Callback<company_browse_job_model> {
                    override fun onResponse(
                        call: Call<company_browse_job_model>,
                        response: Response<company_browse_job_model>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<company_browse_job_model>, t: Throwable) {
                        Log.d("error", t.toString())
                        onResult(null)
                    }
                }
            )
        } else {
            val retrofit = ServiceBuilder(context).GET(CompanyBrowserAPIList::class.java)
            retrofit.getBrowserJob().enqueue(
                object : Callback<company_browse_job_model> {
                    override fun onResponse(
                        call: Call<company_browse_job_model>,
                        response: Response<company_browse_job_model>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<company_browse_job_model>, t: Throwable) {
                        Log.d("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

        data class CompanyActiveHireResponse(
            val code : Int,
            val data : List<CompanyResponse>
        )
        data class CompanyResponse(
            val logo : String,
            val companyName : String,
            val field : String,
            val location : String,
            val userNo : Long
        )
        interface CompanyActiveHire {
            @GET("users/company/active_hire")
            fun getCompanyActiveHire(): Call<CompanyActiveHireResponse>
        }

        fun CompanyActiveHire(context: Context?, onResult: (CompanyActiveHireResponse?) -> Unit) {
                val retrofitAuthorized =
                    ServiceBuilder(context).GET(CompanyActiveHire::class.java)
                    retrofitAuthorized.getCompanyActiveHire().enqueue(
                    object : Callback<CompanyActiveHireResponse> {
                        override fun onResponse(call: Call<CompanyActiveHireResponse>, response: Response<CompanyActiveHireResponse>
                        ) {
                            onResult(response.body())
                        }

                        override fun onFailure(call: Call<CompanyActiveHireResponse>, t: Throwable) {
                            Log.d("error", t.toString())
                            onResult(null)
                        }
                    }
                )
    }
}