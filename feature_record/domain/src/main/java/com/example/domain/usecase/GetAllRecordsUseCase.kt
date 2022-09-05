package com.example.domain.usecase

import com.example.data.repository.RecordRepository


class GetAllRecordsUseCase(private val recordRepository: RecordRepository) {

    suspend operator fun invoke() = recordRepository.getAllRecords()

}