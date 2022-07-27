package com.example.pokegame.data

import com.example.pokegame.domain.UserPoints
import com.example.pokegame.implementation.RecordImplementation

class RecordRepository(private val recordImplementation: RecordImplementation) {

    fun getAllRecords() = recordImplementation.getAllRecords()

}