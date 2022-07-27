package com.example.pokegame.implementation

import com.example.pokegame.data.RecordInterface
import com.example.pokegame.domain.PokemonsApiResult
import com.example.pokegame.domain.Result
import com.example.pokegame.domain.UserPoints

class RecordImplementation(private val recordInterface: RecordInterface) {

    fun getAllRecords(): List<UserPoints>? {
        val callback = recordInterface.getAllRecords().execute()

        return if (callback.isSuccessful) callback.body()
        else null
    }

    fun insertRecord(userPoints: UserPoints): Result? {
        val callback = recordInterface.insertRecord(userPoints.username, userPoints.points, userPoints.team, userPoints.person).execute()

        return if (callback.isSuccessful) callback.body()
        else null
    }

}