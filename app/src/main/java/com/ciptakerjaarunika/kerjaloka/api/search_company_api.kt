package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.search_company_response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class CompanySearchAPI {
    interface CompanySearchUnauthorizedAPIList{
        @GET("/jobseeker/company/search")
        fun getSearchCompany(@Query("keyword") keyword: String): Call<search_company_response>
    }
    interface CompanySearchAuthorizedAPIList{
        @GET("/company/search/u")
        fun getSearchCompanyAuthorized(@Query("keyword") keyword: String): Call<search_company_response>
    }

    fun CompanyGetSearchCompany(context: Context?, keyword: String,onResult: (search_company_response?) -> Unit){
        if (context !== null) {
            val retrofitAuthorized = ServiceBuilder(context).GET(CompanySearchAuthorizedAPIList::class.java)
            retrofitAuthorized.getSearchCompanyAuthorized(keyword).enqueue(
                object : Callback<search_company_response> {
                    override fun onResponse(
                        call: Call<search_company_response>,
                        response: Response<search_company_response>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<search_company_response>, t: Throwable) {
                        Log.d("error", t.toString())
                        onResult(null)
                    }
                }
            )
        } else {
            val retrofit = ServiceBuilder(context).GET(CompanySearchUnauthorizedAPIList::class.java)
            retrofit.getSearchCompany(keyword).enqueue(
                object : Callback<search_company_response> {
                    override fun onResponse(
                        call: Call<search_company_response>,
                        response: Response<search_company_response>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<search_company_response>, t: Throwable) {
                        Log.d("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }
}