package com.example.data.repository

import com.example.data.entity.UserPointsModelRecord
import com.example.data.implementation.RecordImplementation

class RecordRepository(private val recordImplementation: RecordImplementation) {

    suspend fun getAllRecords() = recordImplementation.getAllRecords()
    suspend fun insertRecord(userPoints: UserPointsModelRecord) = recordImplementation.insertRecord(userPoints)

}