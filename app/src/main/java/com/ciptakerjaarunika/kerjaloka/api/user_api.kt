package com.ciptakerjaarunika.kerjaloka.api


import com.ciptakerjaarunika.kerjaloka.model.Job.homejob_model
import com.ciptakerjaarunika.kerjaloka.model.ResponseResult
import com.ciptakerjaarunika.kerjaloka.model.User.user_model
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.*

class UserAPI {
    interface login {
        @GET("users/login")
        fun login(): Call<user_model>
    }
     fun login(onResult: (user_model?) -> Unit){
        val retrofit = ServiceBuilder().GET(login::class.java)

        retrofit.login().enqueue(
            object : Callback<user_model> {
                override fun onFailure(call: Call<user_model>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<user_model>, response: Response<user_model>) {
                    onResult(response.body())
                }
            }
        )
    }

}
