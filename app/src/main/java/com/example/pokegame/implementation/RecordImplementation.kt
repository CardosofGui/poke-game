package com.example.pokegame.implementation

import com.example.pokegame.domain.Result
import com.example.pokegame.domain.UserPoints
import io.ktor.client.*

class RecordImplementation(private val client : HttpClient) {

    suspend fun getAllRecords(): List<UserPoints>? = arrayListOf()

    suspend fun insertRecord(userPoints: UserPoints): Result? = Result("")

}