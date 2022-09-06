package com.core.data.implementation

import com.core.data.errorhandler.ErrorEntity
import com.core.data.errorhandler.ErrorHandler
import io.ktor.client.features.*
import java.io.IOException

class GeneralErrorHandlerImplementation : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when(throwable) {
            is IOException -> ErrorEntity.Network
            is ClientRequestException -> ErrorEntity.ServiceUnavailable
            else -> {
                ErrorEntity.Unknown
            }
        }
    }
}