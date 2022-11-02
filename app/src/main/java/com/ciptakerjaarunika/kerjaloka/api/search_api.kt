package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.search_model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class Search_Api {

    interface getGeneralSearch {
        @GET("mobile/generalsearch")
        fun getGeneralSearch(@Query("keyword") keyword: String?): Call<search_model>
    }

    fun getGeneralSearchAsync(
        context: Context?,
        keyword: String?,
        onResult: (search_model?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).GET(getGeneralSearch::class.java)

        retrofit.getGeneralSearch(keyword).enqueue(
            object : Callback<search_model> {
                override fun onFailure(call: Call<search_model>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<search_model>,
                    response: Response<search_model>
                ) {
                    onResult(response.body())
                }
            }
        )
    }
}