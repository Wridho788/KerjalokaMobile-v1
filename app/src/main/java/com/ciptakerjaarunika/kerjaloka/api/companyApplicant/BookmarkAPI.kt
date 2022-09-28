package com.ciptakerjaarunika.kerjaloka.api.companyApplicant

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction.Model.BookmarkResponse
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyApplicant.ApplicantDetail.Bottomsheet.MoreAction.Model.send_bookmark
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

class BookmarkAPI {
    interface BookmarkAPI {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("company/officer/application/bookmark")
        fun getBookmarkApplicant(
            @Body sendBookmark: send_bookmark,
            @Query("jobNo") jobNo: Long,
            @Query("jobseekerNo") jobseekerNo: Long
        ): Call<BookmarkResponse>
    }

    interface UnBookmarkAPI {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("company/officer/application/unbookmark")
        fun getUnBookmarkApplicant(
            @Body sendBookmark: send_bookmark,
            @Query("jobNo") jobNo: Long,
            @Query("jobseekerNo") jobseekerNo: Long
        ): Call<BookmarkResponse>
    }

    fun BookmarkJob(
        context: Context?,
        sendBookmark: send_bookmark,
        jobNo: Long,
        jobseekerNo: Long,
        bookmark: Boolean,
        onResult: (BookmarkResponse?) -> Unit
    ) {
        if (bookmark) {
            val retrofit = ServiceBuilder(context).POST(UnBookmarkAPI::class.java)
            retrofit.getUnBookmarkApplicant(sendBookmark, jobNo, jobseekerNo).enqueue(
                object : Callback<BookmarkResponse> {
                    override fun onResponse(
                        call: Call<BookmarkResponse>,
                        response: Response<BookmarkResponse>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                        onResult(null)
                    }
                }
            )
        } else {

            val retrofit = ServiceBuilder(context).POST(BookmarkAPI::class.java)
            retrofit.getBookmarkApplicant(sendBookmark, jobNo, jobseekerNo).enqueue(
                object : Callback<BookmarkResponse> {
                    override fun onResponse(
                        call: Call<BookmarkResponse>,
                        response: Response<BookmarkResponse>
                    ) {
                        onResult(response.body())
                    }

                    override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                        onResult(null)
                    }
                }
            )
        }
    }
}
