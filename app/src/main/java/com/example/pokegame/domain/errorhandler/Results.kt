package com.example.pokegame.domain.errorhandler

import com.example.pokegame.data.entities.ErrorEntitity

sealed class Results<T> {

    data class Sucess<T>(val data: T) : Results<T>()

    data class Error<T>(val error: ErrorEntitity) : Results<T>()

}