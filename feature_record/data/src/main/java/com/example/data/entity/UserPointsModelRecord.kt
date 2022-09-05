package com.example.data.entity

import kotlinx.serialization.Serializable

@Serializable
class UserPointsModelRecord(
    val id : Int?,
    var username : String,
    var points : String,
    var team : String,
    var person : String
)

@Serializable
class ResultInsertRecord(
    val status : String
)