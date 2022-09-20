package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.Company.Profile.CompanyProfileResponse
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class company_profile_api {

    interface companyGetProfileData{
        @GET("company/officer/data")
        fun getCompProfileData(): Call<CompanyProfileResponse>
    }

    fun CompanyGetProfileData(context: Context?, onResult: (CompanyProfileResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(companyGetProfileData::class.java)

        retrofit.getCompProfileData().enqueue(
            object : Callback<CompanyProfileResponse>{
                override fun onResponse(
                    call: Call<CompanyProfileResponse>,
                    response: Response<CompanyProfileResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CompanyProfileResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

            }
        )
    }
}