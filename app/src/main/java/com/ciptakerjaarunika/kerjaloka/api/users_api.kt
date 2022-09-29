package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.notifResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class UsersAPI{
    interface userNotification{
        @GET("users/notifications/get")
        fun getNotification(): Call<notifResponse>
    }
    fun GetNotification(context: Context?, onResult: (notifResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(userNotification::class.java)

        retrofit.getNotification().enqueue(
            object : Callback<notifResponse>{
                override fun onResponse(
                    call: Call<notifResponse>,
                    response: Response<notifResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<notifResponse>, t: Throwable) {
                    Log.e("error", t.toString())
                    onResult(null)
                }

            }
        )
    }
}