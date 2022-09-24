package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.Company.Profile.*
import com.ciptakerjaarunika.kerjaloka.model.Job.ReportJobRequest
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

class company_profile_api {

    interface companyGetProfileData{
        @GET("company/officer/data")
        fun getCompProfileData(): Call<CompanyProfileResponse>
    }

    fun CompanyGetProfileData(context: Context?, onResult: (CompanyProfileResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(companyGetProfileData::class.java)

        retrofit.getCompProfileData().enqueue(
            object : Callback<CompanyProfileResponse>{
                override fun onResponse(
                    call: Call<CompanyProfileResponse>,
                    response: Response<CompanyProfileResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CompanyProfileResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

            }
        )
    }

    data class  changeUsernameResponse(val code :Int, val message : String)
    interface changeUsername {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("users/change/username")
        fun changeUsername(@Body changeUsernameRequest: ChangeUsernameRequest) : Call<changeUsernameResponse>
    }

    fun ChangeUsername(username: String, context: Context?, onResult: (changeUsernameResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(changeUsername::class.java)

        retrofit.changeUsername(ChangeUsernameRequest(username)).enqueue(
            object : Callback<changeUsernameResponse>{
                override fun onResponse(
                    call: Call<changeUsernameResponse>,
                    response: Response<changeUsernameResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<changeUsernameResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    interface checkPhoneNumber{
        @GET("users/checkPhone/{phone}")
        fun checkNumber(@Path("phone") phone: String): Call<CheckPhoneResponse>
    }

    fun checkPhone(phone: String, context: Context?, onResult: (CheckPhoneResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(checkPhoneNumber::class.java)

        retrofit.checkNumber(phone).enqueue(
            object : Callback<CheckPhoneResponse>{
                override fun onResponse(
                    call: Call<CheckPhoneResponse>,
                    response: Response<CheckPhoneResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CheckPhoneResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }

    data class  changePhoneResponse(val code :Int, val message : String, val token : String?)
    interface getPhoneNumber{
        @GET("users/change/phone")
        fun getPhoneNumber(@Query("phone")phone: String): Call<changePhoneResponse>
    }
    fun ChangeNumber(phone: String, context: Context?, onResult: (changePhoneResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getPhoneNumber::class.java)

        retrofit.getPhoneNumber(phone).enqueue(
            object : Callback<changePhoneResponse>{
                override fun onResponse(
                    call: Call<changePhoneResponse>,
                    response: Response<changePhoneResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<changePhoneResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }

    data class phoneVerificationResponse(val token: String?, val code: String?)
    interface changeVerification{
        @GET("users/change/phoneVerification")
        fun changeVerification(@Query("token")token: String?, @Query("code")code: String?):Call<phoneVerificationResponse>
    }
    fun ChangeVerification(token: String?, code: String?, context: Context?, onResult:(phoneVerificationResponse?)->Unit){
        val retrofit = ServiceBuilder(context).GET(changeVerification::class.java)

        retrofit.changeVerification(token, code).enqueue(
            object : Callback<phoneVerificationResponse>{
                override fun onResponse(
                    call: Call<phoneVerificationResponse>,
                    response: Response<phoneVerificationResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<phoneVerificationResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }
}

class users {

    interface userData{
        @GET("/users")
        fun getCompProfileData(): Call<user_response>
    }

    fun CompanyGetUserData(context: Context?, onResult: (user_response?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(userData::class.java)

        retrofit.getCompProfileData().enqueue(
            object : Callback<user_response>{
                override fun onResponse(
                    call: Call<user_response>,
                    response: Response<user_response>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<user_response>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }

            }
        )
    }
}

