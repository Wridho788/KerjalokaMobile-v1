package com.ciptakerjaarunika.kerjaloka.ui.HomePage

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/users/home/job")
    fun fetchRecommendationJob(@Query("user_id") tags: String): retrofit2.Call<List<RecommendationJobList>>
}