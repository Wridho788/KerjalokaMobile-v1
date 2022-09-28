package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.Company.Package.Model.MyPackagesResponse
import com.ciptakerjaarunika.kerjaloka.Company.Package.getHistoryResponse
import com.ciptakerjaarunika.kerjaloka.Company.Profile.*
import com.ciptakerjaarunika.kerjaloka.Company.Test.testResponse
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
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

    interface checkEmail{
        @GET("users/check/email")
        fun checkEmail(@Query("keyword") keyword: String): Call<CheckEmailResponse>
    }

    fun checkNewEmail(keyword: String, context: Context?, onResult: (CheckEmailResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(checkEmail::class.java)

        retrofit.checkEmail(keyword).enqueue(
            object : Callback<CheckEmailResponse>{
                override fun onResponse(
                    call: Call<CheckEmailResponse>,
                    response: Response<CheckEmailResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CheckEmailResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }

    data class  changeEmailResponse(val code :Int, val message : String, val token : String?)
    interface getNewEmail{
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("users/change/email")
        fun getEmail(@Body changeEmailRequest: ChangeEmailRequest): Call<changeEmailResponse>
    }
    fun ChangeEmail(email: String, context: Context?, onResult: (changeEmailResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(getNewEmail::class.java)

        retrofit.getEmail(ChangeEmailRequest(email)).enqueue(
            object : Callback<changeEmailResponse>{
                override fun onResponse(
                    call: Call<changeEmailResponse>,
                    response: Response<changeEmailResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<changeEmailResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    data class  changePasswordResponse(val code :Int, val message : String)
    interface getNewPassword{
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("users/change/password")
        fun getPassword(@Body changePasswordRequest: ChangePasswordRequest): Call<changePasswordResponse>
    }
    fun ChangePassword(password: String, newpassword:String, context: Context?, onResult: (changePasswordResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(getNewPassword::class.java)

        retrofit.getPassword(ChangePasswordRequest(password, newpassword)).enqueue(
            object : Callback<changePasswordResponse>{
                override fun onResponse(
                    call: Call<changePasswordResponse>,
                    response: Response<changePasswordResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<changePasswordResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }


    data class discoverResponse(val code: Int?, val message: String?)
    interface Undiscoverable{
        @GET("users/undiscoverable")
        fun setUndiscover(): Call<discoverResponse>
    }

    fun undiscoverable(context: Context?, onResult: (discoverResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(Undiscoverable::class.java)

        retrofit.setUndiscover().enqueue(
            object : Callback<discoverResponse>{
                override fun onResponse(
                    call: Call<discoverResponse>,
                    response: Response<discoverResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<discoverResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    interface Discoverable{
        @GET("users/discoverable")
        fun setDiscover(): Call<discoverResponse>
    }

    fun discoverable(context: Context?, onResult: (discoverResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(Discoverable::class.java)

        retrofit.setDiscover().enqueue(
            object : Callback<discoverResponse>{
                override fun onResponse(
                    call: Call<discoverResponse>,
                    response: Response<discoverResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<discoverResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    interface Newsletter{
        @GET("users/newsletter")
        fun setNewsletter(@Query("newsletter") newsletter: Boolean): Call<discoverResponse>
    }

    fun newsletter(newsletter: Boolean, context: Context?, onResult: (discoverResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(Newsletter::class.java)

        retrofit.setNewsletter(newsletter).enqueue(
            object : Callback<discoverResponse>{
                override fun onResponse(
                    call: Call<discoverResponse>,
                    response: Response<discoverResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<discoverResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    interface companyGetMyPackage{
        @GET("company/officer/mypackage")
        fun getPackageData(): Call<MyPackagesResponse>
    }

    fun CompanyGetPackageData(context: Context?, onResult: (MyPackagesResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(companyGetMyPackage::class.java)

        retrofit.getPackageData().enqueue(
            object : Callback<MyPackagesResponse>{
                override fun onResponse(
                    call: Call<MyPackagesResponse>,
                    response: Response<MyPackagesResponse>
                ) {
                    onResult(response.body())
                }
                override fun onFailure(call: Call<MyPackagesResponse>, t: Throwable) {
                    Log.d("Response Failure", t.toString())
                    onResult(null)
                }
            }
        )
    }

    interface getHistoryPackage{
        @GET("company/officer/package/{packageNo}/{userPackageNo}/history")
        fun historyPackage(@Path("packageNo") packageNo: Int, @Path("userPackageNo") userPackageNo: Int): Call<getHistoryResponse>
    }

    fun HistoryPackage(packageNo: Int, userPackageNo: Int, context: Context?, onResult: (getHistoryResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getHistoryPackage::class.java)

        retrofit.historyPackage(packageNo, userPackageNo).enqueue(
            object : Callback<getHistoryResponse>{
                override fun onResponse(
                    call: Call<getHistoryResponse>,
                    response: Response<getHistoryResponse>
                ) {
                    onResult(response.body())
                }
                override fun onFailure(call: Call<getHistoryResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

    interface getTest{
        @GET("company/officer/tests")
        fun myTest(): Call<testResponse>
    }

    fun MyTest(context: Context?, onResult: (testResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getTest::class.java)

        retrofit.myTest().enqueue(
            object : Callback<testResponse>{
                override fun onResponse(
                    call: Call<testResponse>,
                    response: Response<testResponse>
                ) {
                    onResult(response.body())
                }
                override fun onFailure(call: Call<testResponse>, t: Throwable) {
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

