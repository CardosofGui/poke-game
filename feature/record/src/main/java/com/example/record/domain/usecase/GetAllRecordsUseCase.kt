package com.example.record.domain.usecase

import com.example.record.data.repository.RecordRepository


class GetAllRecordsUseCase(private val recordRepository: RecordRepository) {

    suspend operator fun invoke() = recordRepository.getAllRecords()

}