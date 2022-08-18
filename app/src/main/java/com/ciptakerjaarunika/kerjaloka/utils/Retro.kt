package com.ciptakerjaarunika.kerjaloka.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    val apiHome = "https://api.kerjaloka.com/users/home/job"

    fun getRetroClientInstance(): Retrofit{
        val clientApi = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(apiHome)
            .addConverterFactory(GsonConverterFactory.create(clientApi))
            .build()
    }
}