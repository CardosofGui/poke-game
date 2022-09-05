package com.example.data.errorhandler


interface ErrorHandlerGame {

    fun getError(throwable: Throwable) : ErrorEntityGame

}