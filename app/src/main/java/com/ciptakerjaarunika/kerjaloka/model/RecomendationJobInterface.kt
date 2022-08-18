package com.ciptakerjaarunika.kerjaloka.model

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory

interface RecomendationJobInterface {
    fun getRecomendationJob(): Call<List<RecommendationJob>>

}