package com.example.domain.usecase

import com.example.data.entity.UserPointsModelGame
import com.example.data.repository.InsertRecordRepository

class InsertRecordUseCase(private val insertRecordRepository: InsertRecordRepository) {

    suspend operator fun invoke(userPoints: UserPointsModelGame) = insertRecordRepository.insertRecord(userPoints)

}