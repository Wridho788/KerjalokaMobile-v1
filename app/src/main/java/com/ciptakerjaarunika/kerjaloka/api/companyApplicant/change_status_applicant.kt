package com.ciptakerjaarunika.kerjaloka.api.companyApplicant

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

data class statusResponse( val code: Int, val message: String)
// short list
interface shortlistStatus{
    @GET("company/officer/application/{applicationNo}/shortlist")
    fun shortlistStatus(@Path("applicationNo") applicationNo: Long) : Call<statusResponse>
}
fun ShortlistStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit){
    val retrofit = ServiceBuilder(context).GET(shortlistStatus::class.java)

    retrofit.shortlistStatus(applicationNo).enqueue(
        object : Callback<statusResponse> {
            override fun onResponse(
                call: Call<statusResponse>,
                response: Response<statusResponse>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<statusResponse>, t: Throwable) {
                onResult(null)
            }
        }
    )
}

// test
interface testStatus{
    @GET("company/officer/application/{applicationNo}/test")
    fun testStatus(@Path("applicationNo") applicationNo: Long) : Call<statusResponse>
}
fun TestStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit){
    val retrofit = ServiceBuilder(context).GET(testStatus::class.java)
    retrofit.testStatus(applicationNo).enqueue(
        object : Callback<statusResponse> {
            override fun onResponse(
                call: Call<statusResponse>,
                response: Response<statusResponse>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<statusResponse>, t: Throwable) {
                onResult(null)
            }
        }
    )
}

// interview
interface interviewStatus{
    @GET("company/officer/application/{applicationNo}/interview")
    fun interviewStatus(@Path("applicationNo") applicationNo: Long) : Call<statusResponse>
}
fun InterviewStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit){
    val retrofit = ServiceBuilder(context).GET(interviewStatus::class.java)
    retrofit.interviewStatus(applicationNo).enqueue(
        object : Callback<statusResponse> {
            override fun onResponse(
                call: Call<statusResponse>,
                response: Response<statusResponse>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<statusResponse>, t: Throwable) {
                onResult(null)
            }
        }
    )
}

// accepted
interface acceptedStatus{
    @GET("company/officer/application/{applicationNo}/accept")
    fun acceptedStatus(@Path("applicationNo") applicationNo: Long) : Call<statusResponse>
}
fun AcceptedStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit){
    val retrofit = ServiceBuilder(context).GET(acceptedStatus::class.java)
    retrofit.acceptedStatus(applicationNo).enqueue(
        object : Callback<statusResponse> {
            override fun onResponse(
                call: Call<statusResponse>,
                response: Response<statusResponse>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<statusResponse>, t: Throwable) {
                onResult(null)
            }
        }
    )
}

// rejected
interface rejectedStatus{
    @GET("company/officer/application/{applicationNo}/reject")
    fun rejectedStatus(@Path("applicationNo") applicationNo: Long) : Call<statusResponse>
}
fun RejectedStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit){
    val retrofit = ServiceBuilder(context).GET(rejectedStatus::class.java)
    retrofit.rejectedStatus(applicationNo).enqueue(
        object : Callback<statusResponse> {
            override fun onResponse(
                call: Call<statusResponse>,
                response: Response<statusResponse>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<statusResponse>, t: Throwable) {
                onResult(null)
            }
        }
    )
}

// cv banks
interface cvbankStatus{
    @GET("company/officer/application/{applicationNo}/cvbank")
    fun cvbankStatus(@Path("applicationNo") applicationNo: Long) : Call<statusResponse>
}
fun CvbankStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit){
    val retrofit = ServiceBuilder(context).GET(cvbankStatus::class.java)
    retrofit.cvbankStatus(applicationNo).enqueue(
        object : Callback<statusResponse> {
            override fun onResponse(
                call: Call<statusResponse>,
                response: Response<statusResponse>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<statusResponse>, t: Throwable) {
                onResult(null)
            }
        }
    )
}