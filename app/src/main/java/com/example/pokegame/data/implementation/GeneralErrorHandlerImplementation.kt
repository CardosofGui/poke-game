package com.example.pokegame.data.implementation

import com.example.pokegame.data.entities.ErrorEntitity
import com.example.pokegame.domain.ErrorHandler
import io.ktor.utils.io.errors.*

class GeneralErrorHandlerImplementation : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntitity {
        return when(throwable) {
            is IOException -> ErrorEntitity.Network
            else -> {
                ErrorEntitity.KtorException
            }
        }
    }
}