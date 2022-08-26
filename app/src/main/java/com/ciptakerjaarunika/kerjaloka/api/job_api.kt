package com.ciptakerjaarunika.kerjaloka.api


import android.content.Context
import com.ciptakerjaarunika.kerjaloka.model.Job.homejob_model
import com.ciptakerjaarunika.kerjaloka.model.ResponseResult
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.*

class JobAPI {
    interface getJobHome {
        @GET("users/home/job")
        fun getJobHome(): Call<homejob_model>
    }
     fun getJobHomeAsync(context: Context?, onResult: (homejob_model?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getJobHome::class.java)

        retrofit.getJobHome().enqueue(
            object : Callback<homejob_model> {
                override fun onFailure(call: Call<homejob_model>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<homejob_model>, response: Response<homejob_model>) {
                    onResult(response.body())
                }
            }
        )
    }

}
