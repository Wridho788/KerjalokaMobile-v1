package com.ciptakerjaarunika.kerjaloka.api.companyApplicant

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.CommentResponse
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.send_comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

class CommentAPI {
    interface CommentAPI {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("company/officer/applicant/{jobseekerNo}/comment")
        fun getCommentApplicant(@Path("jobseekerNo") jobseekerNo: Long,  @Body sendComment: send_comment): Call<CommentResponse>
    }

    fun SendCommentPost(
        context: Context?,
        jobseekerNo: Long,
        sendComment: send_comment,
        onResult: (CommentResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder(context).POST(CommentAPI::class.java)

        retrofit.getCommentApplicant(jobseekerNo, sendComment).enqueue(
            object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}