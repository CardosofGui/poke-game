package com.example.pokegame.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokegame.data.entities.UserPoints
import com.example.pokegame.domain.usecase.GetAllRecordsUseCase
import kotlinx.coroutines.launch

class RecordViewModel(private val getAllRecordsUseCase: GetAllRecordsUseCase) : ViewModel() {

    private val _allRecords = MutableLiveData<List<UserPoints>>()
    val allRecords : MutableLiveData<List<UserPoints>>
        get() = _allRecords

    init {
        getAllRecords()
    }
    fun getAllRecords() = viewModelScope.launch {
        _allRecords.postValue(getAllRecordsUseCase())
    }
}