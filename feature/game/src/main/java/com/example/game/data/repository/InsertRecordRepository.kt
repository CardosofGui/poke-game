package com.example.game.data.repository

import com.example.core_android.data.entity.UserPointsModel
import com.example.game.data.implementation.InsertRecordImplementation

class InsertRecordRepository(private val insertRecordImplementation: InsertRecordImplementation) {

    suspend fun insertRecord(userPoints: UserPointsModel) = insertRecordImplementation.insertRecord(userPoints)

}