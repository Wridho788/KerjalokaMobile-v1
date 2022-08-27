package com.ciptakerjaarunika.kerjaloka.api

import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.general_search_model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class Search_Api {

    interface getGeneralSearch {
        @GET("mobile/generalsearch")
        fun getGeneralSearch(): Call<general_search_model>
    }

    fun getGeneralSearchAsync(onResult: (general_search_model?) -> Unit){
        val retrofit = ServiceBuilder().GET(getGeneralSearch::class.java)

        retrofit.getGeneralSearch().enqueue(
            object : Callback<general_search_model> {
                override fun onFailure(call: Call<general_search_model>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<general_search_model>,
                    response: Response<general_search_model>
                ) {
                    onResult(response.body())
                }
            }
        )
    }
}