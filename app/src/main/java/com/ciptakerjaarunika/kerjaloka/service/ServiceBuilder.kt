package com.ciptakerjaarunika.kerjaloka.service

import android.util.Log
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.ResponseResult
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.util.logging.Level.parse

class ServiceBuilder {
    private lateinit var url : String
    private var access_token : String = "";
    private var body : RequestBody? = null;

    constructor(){
    }
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
    }.build()
    private val clientPost  = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("Authorization", access_token)
                builder.header("Content-Type", "application/json")
                builder.header("Accept", "application/json")
                builder.method("POST",  body)
                return@Interceptor chain.proceed(builder.build())
            }
        )
    }.build()



    fun<T> GET(service: Class<T>): T{
        val retrofit = Retrofit.Builder()
            .baseUrl(config().portAddress) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientGet)
            .build()

        return retrofit.create(service)
    }
    fun<T> POST(service: Class<T>): T{
        val retrofit = Retrofit.Builder()
            .baseUrl(config().portAddress) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientPost)
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