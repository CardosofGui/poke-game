package com.example.pokegame.usecase

import com.example.pokegame.data.RecordRepository

class GetAllRecordsUseCase(private val recordRepository: RecordRepository) {

    suspend operator fun invoke() = recordRepository.getAllRecords()

}