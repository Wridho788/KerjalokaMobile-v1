package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch.Model.searchCompanyRequest
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.CompanySearch.Model.search_company_response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class CompanySearchAPI {
    interface SearchCompany {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("users/mobile/searchCompany")
        fun getData(@Body searchRequest: searchCompanyRequest,): Call<search_company_response>
    }

    fun SearchCompany(
        context: Context?,
        searchRequest: searchCompanyRequest,
        onResult: (search_company_response?) -> Unit
    ) {
            val retrofitAuthorized =
            ServiceBuilder(context).POST(SearchCompany::class.java)
            retrofitAuthorized.getData(searchRequest)
                .enqueue(
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
