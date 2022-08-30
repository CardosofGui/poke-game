package com.example.pokegame.data.entities

sealed class ErrorEntitity() {

    object Network : ErrorEntitity()

    object ServiceUnavailable : ErrorEntitity()

    object Unknown : ErrorEntitity()

}