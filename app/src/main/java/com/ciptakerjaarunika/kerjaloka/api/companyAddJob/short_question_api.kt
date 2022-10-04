package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.model.Data.ShortQuestionResponse
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

class short_question_api {
    interface GetShortQuestions {
        @GET("company/officer/job/getpublishedshortquestion")
        fun GetShortQuestions(): Call<ShortQuestionResponse>
    }
    fun getShortQuestion(context: Context?, onResult: (ShortQuestionResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(GetShortQuestions::class.java)
        retrofit.GetShortQuestions().enqueue(
            object : Callback<ShortQuestionResponse> {
                override fun onResponse(
                    call: Call<ShortQuestionResponse>,
                    response: Response<ShortQuestionResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ShortQuestionResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}

class short_question_search {
    interface getShortQuestion{
        @GET("company/officer/{keywords}")
        fun getSearchShortQuestion(@Path("keywords") keywords: String?): Call<ShortQuestionResponse>
    }
    fun getSearchShortQuestion(context: Context?, keywords: String?, onResult: (ShortQuestionResponse?) -> Unit){
        val retrofit = ServiceBuilder(context).GET(getShortQuestion::class.java)

        retrofit.getSearchShortQuestion(keywords).enqueue(
            object : Callback<ShortQuestionResponse> {
                override fun onResponse(
                    call: Call<ShortQuestionResponse>,
                    response: Response<ShortQuestionResponse>
                ) {
                    onResult(response.body())
                }
                override fun onFailure(call: Call<ShortQuestionResponse>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }
}

