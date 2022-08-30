package com.example.pokegame.implementation

import com.example.pokegame.domain.Result
import com.example.pokegame.domain.UserPoints
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class RecordImplementation(private val client : HttpClient) {

    suspend fun getAllRecords(): List<UserPoints>? = client.get(path = "api/pokeRecord/all")
    suspend fun insertRecord(userPoints: UserPoints): Result? {
        return client.post(path = "api/pokeRecord/insert") {
            body = FormDataContent(Parameters.build {
                append("pokegame_username", userPoints.username)
                append("pokegame_points", userPoints.points)
                append("pokegame_team", userPoints.team)
                append("pokegame_person", userPoints.person)
            })
        }
    }

}
/*

 */