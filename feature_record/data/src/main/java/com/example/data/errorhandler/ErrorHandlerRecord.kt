package com.example.data.errorhandler


interface ErrorHandlerRecord {

    fun getError(throwable: Throwable) : ErrorEntityRecord

}