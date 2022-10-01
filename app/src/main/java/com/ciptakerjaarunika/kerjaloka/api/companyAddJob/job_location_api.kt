package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.model.Data.LocationFilter
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class Locations{
    interface GetLocations {
        @GET("data/location")
        fun GetData(): Call<List<LocationFilter>?>
    }

    fun GetLocations(context: Context?, onResult: (List<LocationFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetLocations::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<LocationFilter>?> {
                override fun onResponse(call: Call<List<LocationFilter>?>, response: Response<List<LocationFilter>?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<LocationFilter>?>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}

