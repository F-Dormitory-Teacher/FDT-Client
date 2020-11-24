package com.fdt.client.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetRetrofit {
    val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    var retrofit = Retrofit.Builder()
        .baseUrl("http://172.30.1.5:8080")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService(): Api? {
        return retrofit.create(Api::class.java)
    }
}