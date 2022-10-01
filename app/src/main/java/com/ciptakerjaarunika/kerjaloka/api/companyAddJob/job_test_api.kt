package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Data.TestResponse
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class TestList {
    interface GetTestList{
        @GET("company/officer/job/testList")
        fun GetData(): Call<TestResponse>
    }
    fun GetTest(context: Context?, onResult: (TestResponse?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetTestList::class.java)

        retrofit.GetData().enqueue(
            object : Callback<TestResponse>{
                override fun onResponse(call: Call<TestResponse?>, response: Response<TestResponse>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<TestResponse?>, t: Throwable) {
                    Log.d("res", t.toString())
                    onResult(null)
                }
            }
        )
    }
}