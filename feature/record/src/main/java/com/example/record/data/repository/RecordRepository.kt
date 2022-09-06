package com.example.record.data.repository

import com.example.record.data.implementation.RecordImplementation

class RecordRepository(private val recordImplementation: RecordImplementation) {

    suspend fun getAllRecords() = recordImplementation.getAllRecords()

}