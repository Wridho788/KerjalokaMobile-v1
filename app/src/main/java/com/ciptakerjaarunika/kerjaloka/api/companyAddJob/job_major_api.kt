package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Data.Major
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class Majors {
    interface major {
        @GET("data/majors")
        fun getData(): Call<List<Major>?>
    }

    fun GetMajors(
        context: Context?,
        onResult: (List <Major>?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(major::class.java)

            retrofit.getData().enqueue(
                object : Callback<List<Major>?> {
                    override fun onResponse(
                        call: Call<List<Major>?>,
                        response: Response<List<Major>?>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<List<Major>?>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

}