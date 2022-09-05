package com.example.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.UserPointsModelRecord
import com.example.data.errorhandler.ErrorEntityRecord
import com.example.data.errorhandler.ResultsRecord
import com.example.domain.usecase.GetAllRecordsUseCase
import kotlinx.coroutines.launch

class RecordViewModel(private val getAllRecordsUseCase: GetAllRecordsUseCase) : ViewModel() {

    private val _allRecords = mutableStateListOf<UserPointsModelRecord>()
    val allRecords : List<UserPointsModelRecord>
        get() = _allRecords

    var errorStatus by mutableStateOf("")

    fun getAllRecords() = viewModelScope.launch {
        when(val result = getAllRecordsUseCase.invoke()) {
            is ResultsRecord.Sucess -> {
                _allRecords.clear()
                _allRecords.addAll(result.data)
            }
            is ResultsRecord.Error -> {
                errorStatus = when(result.error)  {
                    is ErrorEntityRecord.Network -> "Conexão com a Internet Falhou"
                    is ErrorEntityRecord.ServiceUnavailable -> "Serviço Indisponivel"
                    is ErrorEntityRecord.Unknown -> "Falha ao Receber Dados"
                }
            }
        }
    }
}