package com.example.data.implementation

import com.example.data.errorhandler.ErrorEntityGame
import com.example.data.errorhandler.ErrorHandlerGame
import io.ktor.client.features.*
import java.io.IOException

class GeneralErrorHandlerGameGameImplementation : ErrorHandlerGame {
    override fun getError(throwable: Throwable): ErrorEntityGame {
        return when(throwable) {
            is IOException -> ErrorEntityGame.Network
            is ClientRequestException -> ErrorEntityGame.ServiceUnavailable
            else -> {
                ErrorEntityGame.Unknown
            }
        }
    }

}