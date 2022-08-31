package com.example.pokegame.data.implementation

import com.example.pokegame.data.entities.ErrorEntitity
import com.example.pokegame.domain.errorhandler.ErrorHandler
import io.ktor.client.features.*
import io.ktor.utils.io.errors.*
import java.util.concurrent.CancellationException

class GeneralErrorHandlerImplementation : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntitity {
        return when(throwable) {
            is IOException -> ErrorEntitity.Network
            is ClientRequestException -> ErrorEntitity.ServiceUnavailable
            else -> {
                ErrorEntitity.Unknown
            }
        }
    }

}