package com.fdt.client.data.remote

import com.fdt.client.entity.Auth
import com.fdt.client.entity.Email
import com.fdt.client.entity.User
import com.fdt.client.entity.response.Token
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("/auth")
    fun postSignIn(@Body user: User): Call<Token>

    @POST("/attend/createAttend")
    fun postAttend(@Header("Authorization") jwt: String): Call<Void>

    @POST("/auth/auth-code")
    fun requestVerifyCode(@Body email: Email): Call<Void>

    @GET("/auth")
    fun confirmVerifyCode(@Query("auth-code") code: String): Call<Void>

    @POST("/auth/register")
    fun postSignUp(@Body auth: Auth): Call<Void>
}