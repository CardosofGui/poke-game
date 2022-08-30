package com.example.pokegame.domain

import kotlinx.serialization.Serializable

@Serializable
class UserPoints(
    val id : Int?,
    var username : String,
    var points : String,
    var team : String,
    var person : String
)

@Serializable
class Result(
    val status : String
)