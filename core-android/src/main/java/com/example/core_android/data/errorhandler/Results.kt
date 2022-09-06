package com.core.data.errorhandler

sealed class Results<T> {

    data class Sucess<T>(val data: T) : Results<T>()

    data class Error<T>(val error: ErrorEntity) : Results<T>()

}