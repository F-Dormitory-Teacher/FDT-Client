package com.fdt.client.entity.response

data class ResponseNotice(
    val notices : ArrayList<DetailNotice>
)

data class ResponseDetailNotice(
    val notice : DetailNotice
)

data class DetailNotice(
    val idx: Int,
    val title: String,
    val content: String,
    val type: String,
    val date: String,
    val createdAt: String
)