package com.fdt.client.data.remote

import com.fdt.client.entity.User
import com.fdt.client.entity.response.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("/auth")
    fun postSignIn(@Body user: User) : Call<Token>

    @POST("/attend/createAttend")
    fun postAttend() : Call<Void>
}