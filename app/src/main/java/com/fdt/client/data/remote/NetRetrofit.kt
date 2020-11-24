package com.fdt.client.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetRetrofit {
    var retrofit = Retrofit.Builder()
        .baseUrl("http://172.30.1.5:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var service: Api = retrofit.create(Api::class.java)

    fun getService(): Api? {
        return service
    }
}