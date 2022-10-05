package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.CompanyDetail.company_detail_model
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

class CompanyDetailAPI {
    interface CompanyDetailAPIList {
        @GET("/companies/{CompanyNo}")
        fun getCompanyDetail(@Path("CompanyNo") CompanyNo: Long): Call<company_detail_model>
    }

    fun getCompanyDetailAsync(
        context: Context?,
        CompanyNo: Long,
        onResult: (company_detail_model?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(CompanyDetailAPIList::class.java)

            retrofit.getCompanyDetail(CompanyNo).enqueue(
                object : Callback<company_detail_model> {
                    override fun onResponse(
                        call: Call<company_detail_model>,
                        response: Response<company_detail_model>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<company_detail_model>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

    interface followCompany{
        @GET("jobseeker/follow/{companyNo}")
        fun sendData( @Path("companyNo") companyNo: Long): Call<ManageProfileAPI.responseGeneral>
    }
    interface unFollowCompany{
        @GET("jobseeker/unfollow/{companyNo}")
        fun sendData( @Path("companyNo") companyNo: Long): Call<ManageProfileAPI.responseGeneral>
    }

    fun ManageFollowCompany(followed: Boolean, companyNo : Long, context: Context?, onResult: (ManageProfileAPI.responseGeneral?) -> Unit){
        if(!followed) {
            val retrofit = ServiceBuilder(context).GET(followCompany::class.java)
            retrofit.sendData(companyNo).enqueue(
                object : Callback<ManageProfileAPI.responseGeneral> {
                    override fun onResponse(
                        call: Call<ManageProfileAPI.responseGeneral>,
                        response: Response<ManageProfileAPI.responseGeneral>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(
                        call: Call<ManageProfileAPI.responseGeneral>,
                        t: Throwable
                    ) {
                        onResult(null)
                    }
                }
            )
        }else{
            val retrofit = ServiceBuilder(context).GET(unFollowCompany::class.java)
            retrofit.sendData(companyNo).enqueue(
                object : Callback<ManageProfileAPI.responseGeneral> {
                    override fun onResponse(
                        call: Call<ManageProfileAPI.responseGeneral>,
                        response: Response<ManageProfileAPI.responseGeneral>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(
                        call: Call<ManageProfileAPI.responseGeneral>,
                        t: Throwable
                    ) {
                        onResult(null)
                    }
                }
            )
        }
    }
}