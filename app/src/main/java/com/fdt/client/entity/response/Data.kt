package com.fdt.client.entity.response

data class Data(
    val data: ResponseFile,
    val lostProducts: ArrayList<DetailLost>
)