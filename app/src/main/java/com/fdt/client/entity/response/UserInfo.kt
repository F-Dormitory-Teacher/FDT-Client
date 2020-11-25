package com.fdt.client.entity.response

data class UserInfo(
    val data: ResponseUserInfo
)

data class ResponseUserInfo(
    val user: DetailUserInfo
)

data class DetailUserInfo(
    val idx: Int,
    val email: String,
    val name: String,
    val studentId: String,
    val createdAt: String
)
