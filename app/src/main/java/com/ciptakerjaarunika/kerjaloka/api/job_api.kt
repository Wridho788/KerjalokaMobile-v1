package com.ciptakerjaarunika.kerjaloka.api

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Job.*
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.job
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobDetailResponse
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rjob_model
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.experience
import com.ciptakerjaarunika.kerjaloka.ui.Screens.JobSearch.SearchJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

class JobAPI {
    interface getJobHome {
        @GET("users/home/job")
        fun getJobHome(): Call<rjob_model>
    }
     fun getJobHomeAsync(context: Context?, onResult: (rjob_model?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getJobHome::class.java)

        retrofit.getJobHome().enqueue(
            object : Callback<rjob_model> {
                override fun onFailure(call: Call<rjob_model>, t: Throwable) {
                    Log.d("Response API", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<rjob_model>, response: Response<rjob_model>) {
                    onResult(response.body())
                }
            }
        )
    }
    interface getJobDetailLogin {
        @GET("/users/jobseeker/job/{CompanyNo}/{JobNo}")
        fun getJobDetailLogin(@Path("CompanyNo") CompanyNo: Long?, @Path("JobNo") JobNo: Long) : Call<rJobDetailResponse>
    }

    interface getJobDetail {
        @GET("/job/{CompanyNo}/{JobNo}/Visitor")
        fun getJobDetail(@Path("CompanyNo") CompanyNo: Long?, @Path("JobNo") JobNo: Long) : Call<rJobDetailResponse>
    }

    fun getJobDetailAsync(context: Context?,CompanyNo:Long?, JobNo:Long,onResult: (rJobDetailResponse?) -> Unit){
        if(SessionManager(context).user == null) {
            val retrofit = ServiceBuilder(context).GET(getJobDetail::class.java)
            retrofit.getJobDetail(CompanyNo, JobNo).enqueue(
                object : Callback<rJobDetailResponse> {
                    override fun onFailure(call: Call<rJobDetailResponse>, t: Throwable) {
                        Log.d("Response API", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<rJobDetailResponse>,
                        response: Response<rJobDetailResponse>
                    ) {
                        onResult(response.body())
                    }
                }
            )
        }
        else{
            val retrofit = ServiceBuilder(context).GET(getJobDetailLogin::class.java)
            retrofit.getJobDetailLogin(CompanyNo, JobNo).enqueue(
                object : Callback<rJobDetailResponse> {
                    override fun onFailure(call: Call<rJobDetailResponse>, t: Throwable) {
                        onResult(null)
                    }
                    override fun onResponse( call: Call<rJobDetailResponse>, response: Response<rJobDetailResponse>) {
                        onResult(response.body())
                    }
                }
            )
        }
    }


    fun GetJobDetailLogin(context: Context?,CompanyNo:Long, JobNo:Long,onResult: (rJobDetailResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getJobDetailLogin::class.java)

        retrofit.getJobDetailLogin(CompanyNo, JobNo).enqueue(
            object : Callback<rJobDetailResponse> {
                override fun onFailure(call: Call<rJobDetailResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<rJobDetailResponse>, response: Response<rJobDetailResponse>) {
                    onResult(response.body())
                }
            }
        )
    }


    //Jobseeker Get All his Applications
    interface getMyApplications {
        @GET("jobseeker/applications")
        fun getList() : Call<myApplicationsResponse>
    }

    fun GetMyAPplications(context: Context?,onResult: (myApplicationsResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getMyApplications::class.java)

        retrofit.getList().enqueue(
            object : Callback<myApplicationsResponse> {
                override fun onFailure(call: Call<myApplicationsResponse>, t: Throwable) {
                    Log.d("Response API", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<myApplicationsResponse>, response: Response<myApplicationsResponse>) {
                    onResult(response.body())
                }
            }
        )
    }

    // Withdraw Job
    data class  withdrawResponse(val code :Int, val Messgae : String)
    interface withdrawJob {
        @GET("jobseeker/withdraw/{JobNo}")
        fun withdrawJob(@Path("JobNo") JobNo: Long) : Call<withdrawResponse>
    }

    fun WithdrawJob(context: Context?, JobNo:Long,onResult: (withdrawResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(withdrawJob::class.java)

        retrofit.withdrawJob(JobNo).enqueue(
            object : Callback<withdrawResponse> {
                override fun onFailure(call: Call<withdrawResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<withdrawResponse>, response: Response<withdrawResponse>) {
                    onResult(response.body())
                }
            }
        )
    }

    // Report Job
    data class  reportJobResponse(val code :Int, val message : String)
    interface reportJob {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/job/{JobNo}/report")
        fun reportJob(@Path("JobNo") JobNo: Long, @Body reportJobRequest: ReportJobRequest) : Call<reportJobResponse>
    }

    fun ReportJob(JobNo:Long, Message : String, context: Context?,onResult: (reportJobResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).POST(reportJob::class.java)

        retrofit.reportJob(JobNo, ReportJobRequest(JobNo, SessionManager(context).user?.userNo!!, Message)).enqueue(
            object : Callback<reportJobResponse> {
                override fun onFailure(call: Call<reportJobResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<reportJobResponse>, response: Response<reportJobResponse>) {
                    onResult(response.body())
                }
            }
        )
    }

    //Bookmark Job
    interface bookmarkJob {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/bookmark")
        fun start(@Body bookmarkJob: BookmarkJob) : Call<BookmarkResponse>
    }
    //UnBookmark Job
    interface unbookmarkJob {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("jobseeker/unbookmark")
        fun start(@Body bookmarkJob: BookmarkJob) : Call<BookmarkResponse>
    }

    fun BookmarkJob(jobNo : Long, bookmark: Boolean, context: Context?,onResult: (BookmarkResponse?) -> Unit){
        if(bookmark){
            var retrofit = ServiceBuilder(context).POST(bookmarkJob::class.java)
            retrofit.start(BookmarkJob(jobNo, SessionManager(context).user?.userNo!!)).enqueue(
                object : Callback<BookmarkResponse> {
                    override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                        onResult(null)
                    }
                    override fun onResponse( call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                        onResult(response.body())
                    }
                }
            )
        }
        else{
            var retrofit = ServiceBuilder(context).POST(unbookmarkJob::class.java)
            retrofit.start(BookmarkJob(jobNo, SessionManager(context).user?.userNo!!)).enqueue(
                object : Callback<BookmarkResponse> {
                    override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                        onResult(null)
                    }
                    override fun onResponse( call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                        onResult(response.body())
                    }
                }
            )
        }

    }

    data class jobRecommendationResponse(
        val code : Int,
        val data : List<SearchJobModel>
    )
    interface getJobRecommendationAuth {
        @GET("jobseeker/job/recommendation")
        fun getData(@Query("getAll") getAll : Boolean) : Call<jobRecommendationResponse>
    }

    fun getJobRecommendation(getAll : Boolean, context: Context?, onResult: (jobRecommendationResponse?) -> Unit){
            val retrofit = ServiceBuilder(context).GET(getJobRecommendationAuth::class.java)

            retrofit.getData(getAll).enqueue(
                object : Callback<jobRecommendationResponse> {
                    override fun onFailure(call: Call<jobRecommendationResponse>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<jobRecommendationResponse>,
                        response: Response<jobRecommendationResponse>
                    ) {
                        onResult(response.body())
                    }
                }
            )
    }

    interface getBookmarkedJob {
        @GET("jobseeker/bookmark")
        fun getData() : Call<jobRecommendationResponse>
    }

    fun getBookmarkedJob(context: Context?, onResult: (jobRecommendationResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getBookmarkedJob::class.java)

        retrofit.getData().enqueue(
            object : Callback<jobRecommendationResponse> {
                override fun onFailure(call: Call<jobRecommendationResponse>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<jobRecommendationResponse>,
                    response: Response<jobRecommendationResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    interface getNearJob {
        @GET("jobseeker/nearMe")
        fun getData(@Query("latitude")latitude: String,@Query("longtitude")longtitude: String ) : Call<jobRecommendationResponse>
    }

    fun getNearJob(latitude : String,longtitude: String, context: Context?, onResult: (jobRecommendationResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getNearJob::class.java)

        retrofit.getData(latitude, longtitude).enqueue(
            object : Callback<jobRecommendationResponse> {
                override fun onFailure(call: Call<jobRecommendationResponse>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<jobRecommendationResponse>,
                    response: Response<jobRecommendationResponse>
                ) {
                    onResult(response.body())
                }
            }
        )
    }

    data class topSearchResponse(
        val code: Int,
        val message: String,
        val data: List<topSearchModel>
    )
    data class topSearchModel(
        val keyword : String,
        val count : Int
    )
    interface getTopSearch {
        @GET("users/job/topsearch")
        fun getData() : Call<topSearchResponse>
    }

    fun GetTopSearch(context: Context?, onResult: (topSearchResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getTopSearch::class.java)

        retrofit.getData().enqueue(
            object : Callback<topSearchResponse> {
                override fun onFailure(call: Call<topSearchResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<topSearchResponse>, response: Response<topSearchResponse> ) {
                    onResult(response.body())
                }
            }
        )
    }

    interface getRelatedJOb {
        @GET("users/job/related")
        fun getData(@Query("jobNo")jobNo: Long) : Call<jobRecommendationResponse>
    }

    fun getRelatedJob(jobNo: Long, context: Context?, onResult: (jobRecommendationResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getRelatedJOb::class.java)

        retrofit.getData(jobNo).enqueue(
            object : Callback<jobRecommendationResponse> {
                override fun onFailure(call: Call<jobRecommendationResponse>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<jobRecommendationResponse>,
                    response: Response<jobRecommendationResponse>
                ) {
                    if(response.body()?.code == 210){
                        onResult(response.body())
                    }
                    else{
                        onResult(null)
                    }
                }
            }
        )
    }

    data class searchJobRequest(
        var keyword: String?,
        var locations : List<Int>,
        var jobtypes : List<Int>,
        var skills : List<Int>,
        var experienceLevel : List<Int>,
        var salaryMin : Int?,
        var salaryMax : Int?,
        var page : Int,
    )
    interface searchJob {
        @Headers("Content-Type: application/json","Accept: application/json")
        @POST("users/mobile/search_job")
        fun getData(@Body search : searchJobRequest) : Call<jobRecommendationResponse>
    }

    fun SearchJob(search : searchJobRequest, context: Context?, onResult: (jobRecommendationResponse?) -> Unit) {
            val retrofit = ServiceBuilder(context).POST(searchJob::class.java)

            retrofit.getData(search).enqueue(
                object : Callback<jobRecommendationResponse> {
                    override fun onFailure(call: Call<jobRecommendationResponse>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<jobRecommendationResponse>,
                        response: Response<jobRecommendationResponse>
                    ) {
                        if (response.body()?.code == 210) {
                            onResult(response.body())
                        } else {
                            onResult(null)
                        }
                    }
                }
            )
    }
}
