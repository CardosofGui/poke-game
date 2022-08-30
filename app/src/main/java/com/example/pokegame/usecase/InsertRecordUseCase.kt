package com.example.pokegame.usecase

import com.example.pokegame.data.RecordRepository
import com.example.pokegame.domain.UserPoints

class InsertRecordUseCase(private val recordRepository: RecordRepository) {

    suspend operator fun invoke(userPoints: UserPoints) = recordRepository.insertRecord(userPoints)

}