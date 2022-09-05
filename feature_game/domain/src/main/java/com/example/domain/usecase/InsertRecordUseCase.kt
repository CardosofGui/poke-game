package com.example.domain.usecase

import com.core.data.entity.UserPointsModel
import com.example.data.repository.InsertRecordRepository

class InsertRecordUseCase(private val insertRecordRepository: InsertRecordRepository) {

    suspend operator fun invoke(userPoints: UserPointsModel) = insertRecordRepository.insertRecord(userPoints)

}