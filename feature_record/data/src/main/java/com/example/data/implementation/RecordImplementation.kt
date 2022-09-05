package com.example.data.implementation

import com.example.data.entity.ResultInsertRecord
import com.example.data.entity.UserPointsModelRecord
import com.example.data.errorhandler.ResultsRecord
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class RecordImplementation(private val client : HttpClient, private val generalErrorHandlerImplementation: GeneralErrorHandlerRecordImplementation) {

    suspend fun getAllRecords(): ResultsRecord<List<UserPointsModelRecord>> {
        return try {
            val response : List<UserPointsModelRecord> = client.get(path = "api/pokeRecord/all")

            ResultsRecord.Sucess(response)
        } catch (t : Throwable) {
            ResultsRecord.Error(generalErrorHandlerImplementation.getError(t))
        }
    }
    suspend fun insertRecord(userPoints: UserPointsModelRecord): ResultsRecord<ResultInsertRecord> {
        return try {
            val response : ResultInsertRecord = client.submitForm(
                path = "api/pokeRecord/insert",
                formParameters = Parameters.build {
                    append("pokegame_username", userPoints.username)
                    append("pokegame_points", userPoints.points)
                    append("pokegame_team", userPoints.team)
                    append("pokegame_person", userPoints.person)
                }
            )
            ResultsRecord.Sucess(response)
        } catch (t : Throwable) {
            ResultsRecord.Error(generalErrorHandlerImplementation.getError(t))
        }
    }
}