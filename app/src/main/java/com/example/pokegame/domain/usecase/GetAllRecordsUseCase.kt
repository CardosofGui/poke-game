package com.example.pokegame.domain.usecase

import com.example.pokegame.data.repository.RecordRepository

class GetAllRecordsUseCase(private val recordRepository: RecordRepository) {

    suspend operator fun invoke() = recordRepository.getAllRecords()

}