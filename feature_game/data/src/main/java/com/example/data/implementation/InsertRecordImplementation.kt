package com.example.data.implementation

import com.example.data.entity.ResultInsertGame
import com.example.data.entity.UserPointsModelGame
import com.example.data.errorhandler.ResultsGame
import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class InsertRecordImplementation(private val client : HttpClient, private val generalErrorHandlerGameImplementation: GeneralErrorHandlerGameGameImplementation) {

    suspend fun insertRecord(userPoints: UserPointsModelGame): ResultsGame<ResultInsertGame> {
        return try {
            val response : ResultInsertGame = client.submitForm(
                path = "api/pokeRecord/insert",
                formParameters = Parameters.build {
                    append("pokegame_username", userPoints.username)
                    append("pokegame_points", userPoints.points)
                    append("pokegame_team", userPoints.team)
                    append("pokegame_person", userPoints.person)
                }
            )
            ResultsGame.Sucess(response)
        } catch (t : Throwable) {
            ResultsGame.Error(generalErrorHandlerGameImplementation.getError(t))
        }
    }
}