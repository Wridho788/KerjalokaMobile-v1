package com.ciptakerjaarunika.kerjaloka.service

import android.content.Context
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceBuilder(context: Context?) {
    private lateinit var url : String

    private var access_token : String = SessionManager(context).access_token.toString();
    private var body : RequestBody? = null;

//    constructor(url : String){
//        this.url = url
//    }
//    constructor(url : String, sendData : RequestBody?){
//        this.url = url
//        this.body = sendData
//    }

    private val clientGet  = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("Authorization", access_token)
                builder.method("GET", null)
                return@Interceptor chain.proceed(builder.build())
            }
        )
    }
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    private fun clientPost(body : RequestBody): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("Authorization", access_token)
                    builder.header("Content-Type", "application/json")
                    builder.header("Accept", "application/json")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()
    }
    private val clientPostFile  = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("Authorization", access_token)
//                builder.method("POST", body)
                return@Interceptor chain.proceed(builder.build())
            }
        )
    }.build()

    fun<T> GET(service: Class<T>): T{
        Log.d("Builder Access Token : ", access_token)
        val retrofit = Retrofit.Builder()
            .baseUrl(config().portAddress) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientGet)
            .build()
//        Log.d("Builder Client Get : ", clientGet.toString())

        return retrofit.create(service)
    }
    fun<T> POST(service: Class<T>): T{
        val retrofit = Retrofit.Builder()
            .baseUrl(config().portAddress) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientPostFile)
            .build()
        return retrofit.create(service)
    }
    fun<T> POSTFILE(service: Class<T>): T{
        val retrofit = Retrofit.Builder()
            .baseUrl(config().portAddress) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientPostFile)
            .build()
        return retrofit.create(service)
    }


//    private var client = OkHttpClient.Builder().build()
//    fun GET(): Response {
//            val request = Request.Builder()
//                .url(url)
//                .addHeader("Authorization", access_token)
//                .get()
//                .build()
//
//        return client.newCall(request).execute();
//    }
}