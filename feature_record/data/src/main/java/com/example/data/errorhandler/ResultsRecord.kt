package com.example.data.errorhandler

sealed class ResultsRecord<T> {

    data class Sucess<T>(val data: T) : ResultsRecord<T>()

    data class Error<T>(val error: ErrorEntityRecord) : ResultsRecord<T>()

}