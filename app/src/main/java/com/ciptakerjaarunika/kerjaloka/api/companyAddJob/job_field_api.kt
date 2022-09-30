package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.model.Data.Field
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class JobFields {

    interface GetJobFields {
        @GET("data/fields")
        fun GetData(): Call<List<Field>>
    }

    fun GetJobFields(context: Context?, onResult: (List<Field>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetJobFields::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<Field>> {
                override fun onResponse(
                    call: Call<List<Field>>, response: Response<List<Field>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<Field>>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

}