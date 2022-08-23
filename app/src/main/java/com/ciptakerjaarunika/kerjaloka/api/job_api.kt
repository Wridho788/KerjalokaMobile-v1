package com.ciptakerjaarunika.kerjaloka.api

import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rjob_model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class JobAPI {
    interface getJobHome {
        @GET("users/home/job")
        fun getJobHome(): Call<rjob_model>
    }
     fun getJobHomeAsync(onResult: (rjob_model?) -> Unit){
        val retrofit = ServiceBuilder().GET(getJobHome::class.java)

        retrofit.getJobHome().enqueue(
            object : Callback<rjob_model> {
                override fun onFailure(call: Call<rjob_model>, t: Throwable) {
                    Log.d("Response API", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<rjob_model>, response: Response<rjob_model>) {
                    onResult(response.body())
                }
            }
        )
    }
    interface getJobDetail {
        @GET("/job/{CompanyNo}/{JobNo}/Visitor")
        fun getPositionByZip(@Path("CompanyNo") CompanyNo: Long?, cb: Callback<String?>?)
//        @GET("users/home/job")
//        fun getJobDetai(JobNo:Long, CompanyNo:Long): Call<rjob_model>rjob_model
    }
    fun getJobDetail(JobNo:Long, CompanyNo:Long,onResult: (rjob_model?) -> Unit){
        val retrofit = ServiceBuilder().GET(getJobDetail::class.java)

        retrofit.getJobHome().enqueue(
            object : Callback<rjob_model> {
                override fun onFailure(call: Call<rjob_model>, t: Throwable) {
                    Log.d("Response API", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<rjob_model>, response: Response<rjob_model>) {
                    onResult(response.body())
                }
            }
        )
    }

}
