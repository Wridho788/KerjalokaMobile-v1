package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.model.Data.Roles
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class JobRole {
    interface GetJobRole {
        @GET("data/roles")
        fun GetData(): Call<List<Roles>?>
    }

    fun GetJobRole(context: Context?, onResult: (List<Roles>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetJobRole::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<Roles>?> {
                override fun onResponse(call: Call<List<Roles>?>, response: Response<List<Roles>?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<Roles>?>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

}