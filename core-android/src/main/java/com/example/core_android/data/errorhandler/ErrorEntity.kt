package com.core.data.errorhandler

sealed class ErrorEntity() {

    object Network : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()

}