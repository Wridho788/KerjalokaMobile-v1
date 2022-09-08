package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanySearch.Model.location_model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class FilterLocationAPI {
    interface getFilterLocationAPI {
        @GET("data/location")
        fun getFilterLocationAPI(): Call<List<location_model>>
    }

    fun getLocationAsync(context: Context?, onResult: (List<location_model>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(getFilterLocationAPI::class.java)

        retrofit.getFilterLocationAPI().enqueue(
            object : Callback<List<location_model>> {
                override fun onResponse(
                    call: Call<List<location_model>>,
                    response: Response<List<location_model>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<location_model>>, t: Throwable) {
                    Log.d("response api", t.toString())
                    onResult(null)
                }
            }
        )
    }


}