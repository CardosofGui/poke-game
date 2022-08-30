package com.example.pokegame.data.entities

sealed class ErrorEntitity {

    object Network : ErrorEntitity()

    object NotFound : ErrorEntitity()

    object AcessDenied : ErrorEntitity()

    object ServiceUnavailable : ErrorEntitity()

    object KtorException : ErrorEntitity()

}