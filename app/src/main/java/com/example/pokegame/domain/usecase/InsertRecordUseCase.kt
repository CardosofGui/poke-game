package com.example.pokegame.domain.usecase

import com.example.pokegame.data.repository.RecordRepository
import com.example.pokegame.data.entities.UserPointsModel

class InsertRecordUseCase(private val recordRepository: RecordRepository) {

    suspend operator fun invoke(userPoints: UserPointsModel) = recordRepository.insertRecord(userPoints)

}