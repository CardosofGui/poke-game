package com.example.data.repository

import com.example.data.entity.UserPointsModelGame
import com.example.data.implementation.InsertRecordImplementation

class InsertRecordRepository(private val insertRecordImplementation: InsertRecordImplementation) {

    suspend fun insertRecord(userPoints: UserPointsModelGame) = insertRecordImplementation.insertRecord(userPoints)

}