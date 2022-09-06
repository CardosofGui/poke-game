package com.example.game.data.implementation

import com.example.core_android.data.entity.ResultInsert
import com.example.core_android.data.entity.UserPointsModel
import com.core.data.errorhandler.Results
import com.core.data.implementation.GeneralErrorHandlerImplementation
import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class InsertRecordImplementation(private val client : HttpClient, private val generalErrorHandlerImplementation: GeneralErrorHandlerImplementation) {

    suspend fun insertRecord(userPoints: UserPointsModel): Results<ResultInsert> {
        return try {
            val response : ResultInsert = client.submitForm(
                path = "api/pokeRecord/insert",
                formParameters = Parameters.build {
                    append("pokegame_username", userPoints.username)
                    append("pokegame_points", userPoints.points)
                    append("pokegame_team", userPoints.team)
                    append("pokegame_person", userPoints.person)
                }
            )
            Results.Sucess(response)
        } catch (t : Throwable) {
            Results.Error(generalErrorHandlerImplementation.getError(t))
        }
    }
}