package com.example.pokegame.domain

import com.example.pokegame.data.entities.ErrorEntitity

interface ErrorHandler {

    fun getError(throwable: Throwable) : ErrorEntitity

}