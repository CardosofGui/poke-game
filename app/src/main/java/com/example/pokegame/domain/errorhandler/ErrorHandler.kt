package com.example.pokegame.domain.errorhandler

import com.example.pokegame.data.entities.ErrorEntitity

interface ErrorHandler {

    fun getError(throwable: Throwable) : ErrorEntitity

}