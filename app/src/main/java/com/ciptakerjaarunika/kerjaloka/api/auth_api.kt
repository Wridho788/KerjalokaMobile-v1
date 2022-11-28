package com.ciptakerjaarunika.kerjaloka.api


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.model.User.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

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
                    onResult(null)
                }
                override fun onResponse( call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.body() != null) {
                        onResult(response.body())
                    } else {
                        val data: String = response.errorBody()!!.string()
                        try {
                            val jObjError = JSONObject(data)
                            val message = jObjError.getString("message")
                            val code = jObjError.getString("code")
                            onResult(LoginResponse(code.toString(),
                            message.toString()))
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
        )
    }

    interface ICheckLogin {
        @GET("users/self")
        fun checkLogin(): Call<CheckLoginDataResponse>
    }
    fun CheckLogin(context: Context?, mainActivity: MainActivity, onResult: (CheckLoginDataResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(ICheckLogin::class.java)

        retrofit.checkLogin().enqueue(
            object : Callback<CheckLoginDataResponse> {
                override fun onFailure(call: Call<CheckLoginDataResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<CheckLoginDataResponse>, response: Response<CheckLoginDataResponse>) {
                    val data = response.body()
                    if(data != null) {
                        if (data.user == null) {
                            SessionManager(context).user = null
                            SessionManager(context).access_token = null
                            SessionManager(context).chatData = null
                        } else {
                            SessionManager(context).user = data.user
                            val account = Gson().toJson(data.account)
                            val additional = Gson().toJson(data.userAdditional)
                            if(data.user.roleNo == 4){
                                SessionManager(context).user?.jobseekers =
                                    Gson().fromJson(account, Jobseeker::class.java)

                                SessionManager(context).user?.jobseekerAdditional =
                                    Gson().fromJson(additional, JobseekerAdditional::class.java)
                            }
                            else if(data.user.roleNo == 2 || data.user.roleNo > 4){
                                SessionManager(context).user?.company =
                                    Gson().fromJson(account, Company::class.java)

                                SessionManager(context).user?.companyAdditional =
                                    Gson().fromJson(additional, CompanyAdditional::class.java)
                            }
                        }
                        mainActivity.refreshBottomSheet()
                    }
                    return onResult(response.body())
                }
            }
        )
    }
}

class AUTHGOOGLEAPI{
    interface IGoogleLogin{
        @Headers("Content-Type: application/json",
            "Accept: application/json")
        @POST("users/googleLogin")
        fun login(@Body GoogleLoginRequest: GoogleLoginRequest): Call<LoginResponse>
    }
    fun GoogleLogin(context: Context?, GoogleLoginRequest: GoogleLoginRequest,onResult: (LoginResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(IGoogleLogin::class.java)
        retrofit.login(GoogleLoginRequest).enqueue(
            object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("error google login", t.toString())
                    onResult(null)
                }
            }
        )
    }
}
