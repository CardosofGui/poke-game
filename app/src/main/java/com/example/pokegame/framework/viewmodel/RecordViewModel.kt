package com.example.pokegame.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokegame.data.PokemonRepository
import com.example.pokegame.data.RecordRepository
import com.example.pokegame.domain.UserPoints

class RecordViewModel(private val recordRepository: RecordRepository) : ViewModel() {

    private val _allRecords = MutableLiveData<List<UserPoints>>()
    val allRecords : MutableLiveData<List<UserPoints>>
        get() = _allRecords

    fun getAllRecords() {
        Thread {
            _allRecords.postValue(recordRepository.getAllRecords())
        }.start()
    }

    fun resetRecords() {
        _allRecords.postValue(arrayListOf())
    }
}