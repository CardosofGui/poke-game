package com.example.pokegame.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokegame.data.PokemonRepository
import com.example.pokegame.data.RecordRepository
import com.example.pokegame.domain.UserPoints
import kotlinx.coroutines.launch

class RecordViewModel(private val recordRepository: RecordRepository) : ViewModel() {

    private val _allRecords = MutableLiveData<List<UserPoints>>()
    val allRecords : MutableLiveData<List<UserPoints>>
        get() = _allRecords

    fun getAllRecords() = viewModelScope.launch {
        _allRecords.postValue(recordRepository.getAllRecords())
    }

    fun resetRecords() {
        _allRecords.postValue(arrayListOf())
    }
}