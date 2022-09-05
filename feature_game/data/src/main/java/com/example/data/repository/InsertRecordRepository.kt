package com.example.data.repository

import com.core.data.entity.UserPointsModel
import com.example.data.implementation.InsertRecordImplementation

class InsertRecordRepository(private val insertRecordImplementation: InsertRecordImplementation) {

    suspend fun insertRecord(userPoints: UserPointsModel) = insertRecordImplementation.insertRecord(userPoints)

}