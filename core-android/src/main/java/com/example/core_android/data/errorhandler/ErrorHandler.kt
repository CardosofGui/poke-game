package com.core.data.errorhandler


interface ErrorHandler {

    fun getError(throwable: Throwable) : ErrorEntity

}