package com.example.data.errorhandler

sealed class ErrorEntityGame() {

    object Network : ErrorEntityGame()

    object ServiceUnavailable : ErrorEntityGame()

    object Unknown : ErrorEntityGame()

}