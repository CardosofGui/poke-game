package com.example.pokegame.domain.usecase

import com.example.pokegame.data.repository.RecordRepository
import com.example.pokegame.data.entities.UserPoints

class InsertRecordUseCase(private val recordRepository: RecordRepository) {

    suspend operator fun invoke(userPoints: UserPoints) = recordRepository.insertRecord(userPoints)

}