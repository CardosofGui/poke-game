package com.example.pokegame.data.implementation

import com.example.pokegame.data.entities.ResultInsert
import com.example.pokegame.data.entities.UserPointsModel
import com.example.pokegame.domain.errorhandler.Results
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class RecordImplementation(private val client : HttpClient, private val generalErrorHandlerImplementation: GeneralErrorHandlerImplementation) {

    suspend fun getAllRecords(): Results<List<UserPointsModel>> {
        return try {
            val response : List<UserPointsModel> = client.get(path = "api/pokeRecord/all")

            Results.Sucess(response)
        } catch (t : Throwable) {
            Results.Error(generalErrorHandlerImplementation.getError(t))
        }
    }
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