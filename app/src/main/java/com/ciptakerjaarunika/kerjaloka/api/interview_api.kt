package com.ciptakerjaarunika.kerjaloka.api


import android.util.Log
import com.ciptakerjaarunika.kerjaloka.model.Interview.conmpany_interview_list_api
import com.ciptakerjaarunika.kerjaloka.model.Job.homejob_model
import com.ciptakerjaarunika.kerjaloka.model.ResponseResult
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.company_interview_adapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.*

class InterviewAPI {
    interface CompanyGetInterviewList {
        @GET("company/application/getinterview")
        fun getJobHome(): Call<conmpany_interview_list_api>
    }
     fun CompanyGetInterviewList(onResult: (conmpany_interview_list_api?) -> Unit){
        val retrofit = ServiceBuilder().GET(CompanyGetInterviewList::class.java)

        retrofit.getJobHome().enqueue(
            object : Callback<conmpany_interview_list_api> {
                override fun onFailure(call: Call<conmpany_interview_list_api>, t: Throwable) {
                    Log.d("error", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<conmpany_interview_list_api>, response: Response<conmpany_interview_list_api>) {
                    onResult(response.body())
                }
            }
        )
    }

}
