package com.example.data.implementation

import com.example.data.errorhandler.ErrorEntityRecord
import com.example.data.errorhandler.ErrorHandlerRecord
import io.ktor.client.features.*
import io.ktor.utils.io.errors.*

class GeneralErrorHandlerRecordImplementation : ErrorHandlerRecord {
    override fun getError(throwable: Throwable): ErrorEntityRecord {
        return when(throwable) {
            is IOException -> ErrorEntityRecord.Network
            is ClientRequestException -> ErrorEntityRecord.ServiceUnavailable
            else -> {
                ErrorEntityRecord.Unknown
            }
        }
    }

}