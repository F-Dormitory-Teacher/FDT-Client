package com.fdt.client.entity

import java.util.*

data class Response(
    var status : Int,
    var message : String,
    var data : myPage
)

data class myPage (
    var attendances : Array<MyAttend>
)
data class MyAttend (
var idx: Int,
var userIdx : Int,
var type : String,
var status: String,
var date : Date,
var userName : String
)
