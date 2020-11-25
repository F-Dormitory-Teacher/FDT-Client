package com.fdt.client.entity

import java.util.*

data class MyPage(
    var data : ResponseMyPage
)

data class ResponseMyPage (
    var attendances : Array<MyAttend>
)
data class MyAttend (
    var idx: Int,
    var userIdx : Int,
    var type : String,
    var status: String,
    var date : String,
    var userName : String
)