package com.ciptakerjaarunika.kerjaloka.api.companyApplicant

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.JobApplicant.Model.listApplicantResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class CompanyListApplicantAPI {
    data class getApplicantRequest(
        val jobNo: String,
        val answer: List<String>?,
        val applyStatus: Int?,
        val sortType: Int?,
        val cityNo: Int?,
        val gender: List<Char>?,
        val education: List<Int>?,
        val experience: List<Int>?,
        val maxSalary: Int?,
    )

    interface CompanyListApplicantAPI {
        @Headers(
            "Content-Type: application/json", "Accept: application/json"
        )
        @POST("/company/officer/job/{JobNo}/application")
        fun getListApplicant(@Body filter: getApplicantRequest): Call<listApplicantResponse>
    }

    fun GetListApplicantPost(
        context: Context?, JobNo: String, onResult: (listApplicantResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).POST(CompanyListApplicantAPI::class.java)
        retrofit.getListApplicant(getApplicantRequest(JobNo, listOf(), 1, null,null, listOf(), listOf(),
            listOf(),null
        ))
            .enqueue(object : Callback<listApplicantResponse> {
                override fun onResponse(
                    call: Call<listApplicantResponse>, response: Response<listApplicantResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<listApplicantResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
            })
    }
}