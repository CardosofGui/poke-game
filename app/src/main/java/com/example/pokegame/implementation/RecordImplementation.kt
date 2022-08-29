package com.example.pokegame.implementation

import com.example.pokegame.data.RecordInterface
import com.example.pokegame.domain.PokemonsApiResult
import com.example.pokegame.domain.Result
import com.example.pokegame.domain.UserPoints
import io.ktor.client.*
import io.ktor.client.request.*

class RecordImplementation(private val client : HttpClient) {

    suspend fun getAllRecords(): List<UserPoints>? = arrayListOf()

    suspend fun insertRecord(userPoints: UserPoints): Result? = Result("")

}