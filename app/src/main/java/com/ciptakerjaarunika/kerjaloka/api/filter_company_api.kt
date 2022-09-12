package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.industri_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.size_company_model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class FilterLocationAPI {
    interface getFilterLocationAPI {
        @GET("data/location")
        fun getFilterLocationAPI(): Call<List<location_model>>
    }

    fun getLocationAsync(context: Context?, onResult: (List<location_model>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(getFilterLocationAPI::class.java)

        retrofit.getFilterLocationAPI().enqueue(
            object : Callback<List<location_model>> {
                override fun onResponse(
                    call: Call<List<location_model>>,
                    response: Response<List<location_model>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<location_model>>, t: Throwable) {
                    Log.d("response api", t.toString())
                    onResult(null)
                }
            }
        )
    }
}

class FilterIndustriAPI {

    interface getFilterIndustriAPI {
        @GET("data/fields")
        fun getFilterIndustriAPI(): Call<List<industri_model>>
    }

    fun getIndustriAsync(context: Context?, onResult: (List<industri_model>?) -> Unit) {
        val retrofitIndustri = ServiceBuilder(context).GET(getFilterIndustriAPI::class.java)

        retrofitIndustri.getFilterIndustriAPI().enqueue(
            object : Callback<List<industri_model>> {
                override fun onResponse(
                    call: Call<List<industri_model>>,
                    response: Response<List<industri_model>>
                ) {
                    Log.d("response result", response.body().toString())
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<industri_model>>, t: Throwable) {
                    Log.d("response api", t.toString())
                    onResult(null)
                }
            }
        )
    }
}

class FilterSizeCompanyAPI {
    interface getFilterSizeCompanyAPI {
        @GET("data/size")
        fun getFilterSizeCompanyAPI(): Call<List<size_company_model>>
    }

    fun getSizeIndustriAsync(context: Context?, onResult: (List<size_company_model>?) -> Unit) {
        val retrofitSizeCompany = ServiceBuilder(context).GET(getFilterSizeCompanyAPI::class.java)

        retrofitSizeCompany.getFilterSizeCompanyAPI().enqueue(
            object : Callback<List<size_company_model>> {
                override fun onResponse(
                    call: Call<List<size_company_model>>,
                    response: Response<List<size_company_model>>
                ) {
                    Log.d("response result", response.body().toString())
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<size_company_model>>, t: Throwable) {
                    Log.d("response api", t.toString())
                    onResult(null)
                }
            }
        )
    }
}