package com.fdt.client.data.remote

import com.fdt.client.entity.*
import com.fdt.client.entity.response.*
import okhttp3.MultipartBody
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

    @POST("/lost-product")
    fun postLostProduct(@Header("Authorization") jwt: String, @Body lost: Lost): Call<Void>

    @POST("/article/createArticle")
    fun postArticle(@Header("Authorization") jwt: String, @Body article: Article): Call<Void>

    @GET("/attend/getMyAttend")
    fun getMyAttends(@Header("Authorization") jwt: String,
                     @Query("date")date: String,
                     @Query("type")type: String): Call<MyPage>

    @Multipart
    @POST("/upload")
    fun uploadImage(
        @Header("Authorization") jwt: String,
        @Part file: MultipartBody.Part
    ): Call<Data>

    @GET("/article/getArticles")
    fun getArticleList(): Call<ArticleData>

    @GET("/lost-product")
    fun getLostList(): Call<Data>

    @GET("/notice/getNotices")
    fun getAllNoticeList(): Call<NoticeData>

    @GET("/lost-product/getLostInfo/{idx}")
    fun getDetailLost(@Path("idx") id: Int): Call<LostData>
}