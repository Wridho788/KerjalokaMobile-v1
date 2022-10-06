package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Data.Title
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class Majors {
    interface major {
        @GET("data/titles")
        fun getData(): Call<List<Title>?>
    }

    fun GetMajors(
        context: Context?,
        onResult: (List <Title>?) -> Unit
    ) {
        if (context != null) {
            val retrofit = ServiceBuilder(context).GET(major::class.java)

            retrofit.getData().enqueue(
                object : Callback<List<Title>?> {
                    override fun onResponse(
                        call: Call<List<Title>?>,
                        response: Response<List<Title>?>
                    ) {
                        onResult(response.body())
                    }
                    override fun onFailure(call: Call<List<Title>?>, t: Throwable) {
                        Log.e("error", t.toString())
                        onResult(null)
                    }
                }
            )
        }
    }

}