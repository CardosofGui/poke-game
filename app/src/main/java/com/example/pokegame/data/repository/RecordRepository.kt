package com.example.pokegame.data.repository

import com.example.pokegame.data.entities.UserPointsModel
import com.example.pokegame.data.implementation.RecordImplementation

class RecordRepository(private val recordImplementation: RecordImplementation) {

    suspend fun getAllRecords() = recordImplementation.getAllRecords()
    suspend fun insertRecord(userPoints: UserPointsModel) = recordImplementation.insertRecord(userPoints)

}