package com.ciptakerjaarunika.kerjaloka.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retro {
    val Base_URL = "https://api.kerjaloka.com"
    private var mRetrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder().baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return mRetrofit!!
        }
}