package com.example.data.repository

import com.core.data.entity.UserPointsModel
import com.example.data.implementation.RecordImplementation

class RecordRepository(private val recordImplementation: RecordImplementation) {

    suspend fun getAllRecords() = recordImplementation.getAllRecords()
    suspend fun insertRecord(userPoints: UserPointsModel) = recordImplementation.insertRecord(userPoints)

}