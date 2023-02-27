package com.ciptakerjaarunika.kerjaloka.service

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder(context: Context?) {
    private lateinit var url: String

    private var access_token: String = SessionManager(context).access_token.toString()
    private var body: RequestBody? = null

    private val clientGet = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("Authorization", access_token)
            builder.method("GET", null)
            return@Interceptor chain.proceed(builder.build())
        })
    }.build()

    private fun clientPost(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("Authorization", access_token)
                builder.header("Content-Type", "application/json")
                builder.header("Accept", "application/json")
                return@Interceptor chain.proceed(builder.build())
            })
        }.build()
    }

    private val clientPostFile = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("Authorization", access_token)
            return@Interceptor chain.proceed(builder.build())
        })
    }.build()

    fun <T> GET(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(config().portAddress) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create()).client(clientGet).build()
        return retrofit.create(service)
    }

    fun <T> POST(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(config().portAddress) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create()).client(clientPost()).build()
        return retrofit.create(service)
    }

    fun <T> POSTFILE(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(config().portAddress) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create()).client(clientPostFile).build()
        return retrofit.create(service)
    }
}