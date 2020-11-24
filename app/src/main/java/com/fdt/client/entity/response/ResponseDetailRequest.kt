package com.fdt.client.entity.response

data class ResponseDetailRequest(
    val articles : ArrayList<DetailRequest>
)

data class DetailRequest(
    val idx: Int,
    val userIdx: Int,
    val title: String,
    val content: String,
    val location: String,
    val imageUrl: String,
    val status: String,
    val userName: String
)

data class DetailLost(
    val idx: Int,
    val userIdx: Int,
    val title: String,
    val content: String,
    val location: String,
    val imageUrl: String,
    val lostStatus: String,
    val createdAt: String
)