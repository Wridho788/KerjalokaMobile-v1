package com.ciptakerjaarunika.kerjaloka.api


import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Interview.conmpany_interview_list_api
import com.ciptakerjaarunika.kerjaloka.model.User.LoginRequest
import com.ciptakerjaarunika.kerjaloka.model.User.LoginResponse
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

class AUTHAPI {
    interface ILogin {
        @Headers("Content-Type: application/json",
            "Accept: application/json")
        @POST("users/login")
        fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
    }
    fun Login(context: Context?, loginRequest: LoginRequest, onResult: (LoginResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(ILogin::class.java)

        retrofit.login(loginRequest).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    onResult(response.body())
                }
            }
        )
    }
}

