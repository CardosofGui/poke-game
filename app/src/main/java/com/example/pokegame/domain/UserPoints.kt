package com.example.pokegame.domain

data class UserPoints(
    val id : Int?,
    var username : String,
    var points : String,
    var team : String,
    var person : String
)

data class Result(
    val status : String
)