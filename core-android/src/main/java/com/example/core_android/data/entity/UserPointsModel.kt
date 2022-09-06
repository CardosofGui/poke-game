package com.core.data.entity

import kotlinx.serialization.Serializable

@Serializable
class UserPointsModel(
    val id : Int?,
    var username : String,
    var points : String,
    var team : String,
    var person : String
)

@Serializable
class ResultInsert(
    val status : String
)