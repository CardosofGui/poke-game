package com.example.data.errorhandler

sealed class ErrorEntityRecord() {

    object Network : ErrorEntityRecord()

    object ServiceUnavailable : ErrorEntityRecord()

    object Unknown : ErrorEntityRecord()

}