package com.example.data.errorhandler

sealed class ResultsGame<T> {

    data class Sucess<T>(val data: T) : ResultsGame<T>()

    data class Error<T>(val error: ErrorEntityGame) : ResultsGame<T>()

}