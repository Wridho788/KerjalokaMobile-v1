package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.addJobRequest
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.model.addJobResponse
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


class AddJobAPI {
    interface iAddJob {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("company/officer/job/add?publish=true")
        fun addJob(@Body addJobRequest: addJobRequest): Call<addJobResponse>
    }
    interface iEditJob {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("company/officer/job/{jobNo}/edit?publish=true")
        fun addJob(@Path("jobNo")jobNo : Long,  @Body addJobRequest: addJobRequest): Call<addJobResponse>
    }
    fun SendJob(context: Context?, addJobRequest: addJobRequest, onResult: (addJobResponse?) -> Unit){
        if(addJobRequest.JobNo!=null){
            val retrofit = ServiceBuilder(context).POST(iEditJob::class.java)
            retrofit.addJob(addJobRequest.JobNo!!, addJobRequest).enqueue(
                object : Callback<addJobResponse>{
                    override fun onResponse(
                        call: Call<addJobResponse>,
                        response: Response<addJobResponse>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<addJobResponse>, t: Throwable) {
                        onResult(null)
                    }
                }
            )
        }
        else{
            val retrofit = ServiceBuilder(context).POST(iAddJob::class.java)
            retrofit.addJob(addJobRequest).enqueue(
                object : Callback<addJobResponse>{
                    override fun onResponse(
                        call: Call<addJobResponse>,
                        response: Response<addJobResponse>
                    ) {
                        if(response.body() != null){
                            onResult(response.body())
                        }else{
                            val data: String = response.errorBody()!!.string()
                            try {
                                val jObjError = JSONObject(data)
                                Log.d("response", jObjError.toString())

                                Toast.makeText(
                                   context, "Not enough credit, please buy a package first",
                                    Toast.LENGTH_LONG
                                ).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }

                            Log.d("response", response.toString())
                        }
                    }

                    override fun onFailure(call: Call<addJobResponse>, t: Throwable) {
                        Log.d("err", t.toString())
                        onResult(null)
                    }
                }
            )
        }

    }
}