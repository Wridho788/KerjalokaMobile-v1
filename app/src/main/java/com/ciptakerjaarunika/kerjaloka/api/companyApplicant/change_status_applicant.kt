package com.ciptakerjaarunika.kerjaloka.api.companyApplicant

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class statusResponse(val code: Int, val message: String)

// short list
interface shortlistStatus {
    @GET("company/officer/application/{applicationNo}/shortlist")
    fun shortlistStatus(@Path("applicationNo") applicationNo: Long): Call<statusResponse>
}

fun ShortlistStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit) {
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
interface testStatus {
    @GET("company/officer/application/{applicationNo}/test")
    fun testStatus(@Path("applicationNo") applicationNo: Long): Call<statusResponse>
}

fun TestStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit) {
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
interface interviewStatus {
    @GET("company/officer/application/{applicationNo}/interview")
    fun interviewStatus(@Path("applicationNo") applicationNo: Long): Call<statusResponse>
}

interface PostInterviewStatus {
    @POST("/company/interviewSchedule")
    fun postInterviewSchedule(@Body interviewScheduleRequest: InterviewScheduleRequest): Call<statusResponse>
}

fun InterviewStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit) {
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

data class calenderEvent(
    val attendees: List<email>,
    val description: String,
    val end: end,
    val guestsCanInviteOthers: Boolean,
    val guestsCanModify: Boolean,
    val location: String,
    val reminder: reminder,
    val start: start,
    val summary: String,
)
data class email(val email: String)
data class end(val dateTime: String, val timeZone: String)
data class start(val dateTime: String, val timeZone: String)
data class reminder(
    val overrides: List<override>,
    val useDefault: Boolean
)
data class override(
    val minutes: Long
)
data class InterviewScheduleRequest(
   val applicantNo: Long,
    val calenderEvent: calenderEvent,
    val expiredOn: String,
    val token: String,
)


fun InterviewSchedule(
    context: Context?,
    @Body interviewScheduleRequest: InterviewScheduleRequest,
    onResult: (statusResponse?) -> Unit
) {
    val retrofit = ServiceBuilder(context).POST(PostInterviewStatus::class.java)
    retrofit.postInterviewSchedule(interviewScheduleRequest).enqueue(
        object : Callback<statusResponse> {
            override fun onResponse(
                call: Call<statusResponse>,
                response: Response<statusResponse>
            ) {
                if (response.body() != null) {
                    onResult(response.body())
                } else {
                    val data: String = response.errorBody()!!.string()
                    try {
                        val jObjError = JSONObject(data)
                        val message  = jObjError.getString("message")
                        val code = jObjError.getString("code")
//                        onResult(
//
//
//                        )
                        Log.d("error", message.toString())
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<statusResponse>, t: Throwable) {
                onResult(null)            }
        }
    )
}

// accepted
interface acceptedStatus {
    @GET("company/officer/application/{applicationNo}/accept")
    fun acceptedStatus(@Path("applicationNo") applicationNo: Long): Call<statusResponse>
}

fun AcceptedStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit) {
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
interface rejectedStatus {
    @GET("company/officer/application/{applicationNo}/reject")
    fun rejectedStatus(@Path("applicationNo") applicationNo: Long): Call<statusResponse>
}

fun RejectedStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit) {
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
interface cvbankStatus {
    @GET("company/officer/application/{applicationNo}/cvbank")
    fun cvbankStatus(@Path("applicationNo") applicationNo: Long): Call<statusResponse>
}

fun CvbankStatus(context: Context?, applicationNo: Long, onResult: (statusResponse?) -> Unit) {
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