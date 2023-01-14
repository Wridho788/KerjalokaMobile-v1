package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Companyjobdetail.model.getJobResponse
import com.ciptakerjaarunika.kerjaloka.model.Data.CompanyAnalytic
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

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

    interface getCompanyAnalytic {
        @GET("users/analytic/get")
        fun getCompanyAnalytic(@Query("analyticItemType") analyticItemType: Int, @Query("itemNo") itemNo: Long): Call<CompanyAnalytic>
    }
    fun GetCompanyAnalytic(context: Context?, analyticItemType: Int,itemNo: Long, onResult: (CompanyAnalytic?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(getCompanyAnalytic::class.java)
        retrofit.getCompanyAnalytic(analyticItemType, itemNo).enqueue(
            object : Callback<CompanyAnalytic> {
                override fun onResponse(
                    call: Call<CompanyAnalytic>,
                    response: Response<CompanyAnalytic>
                ) {
                    if (response.body() != null) {
                        onResult(response.body())
                    } else {
                        val data: String = response.errorBody()!!.string()
                        try {
                            val jObjError = JSONObject(data)
                            val map = jObjError.getString("message")
                            Toast.makeText(
                                context, map.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                        Log.d("response", response.toString())
                    }
                }

                override fun onFailure(call: Call<CompanyAnalytic>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}