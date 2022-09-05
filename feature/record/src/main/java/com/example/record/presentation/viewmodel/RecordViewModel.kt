package com.example.record.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.entity.UserPointsModel
import com.core.data.errorhandler.ErrorEntity
import com.core.data.errorhandler.Results
import com.example.record.domain.usecase.GetAllRecordsUseCase
import kotlinx.coroutines.launch

class RecordViewModel(private val getAllRecordsUseCase: GetAllRecordsUseCase) : ViewModel() {

    private val _allRecords = mutableStateListOf<UserPointsModel>()
    val allRecords : List<UserPointsModel>
        get() = _allRecords

    var errorStatus by mutableStateOf("")

    fun getAllRecords() = viewModelScope.launch {
        when(val result = getAllRecordsUseCase.invoke()) {
            is Results.Sucess -> {
                _allRecords.clear()
                _allRecords.addAll(result.data)
            }
            is Results.Error -> {
                errorStatus = when(result.error)  {
                    is ErrorEntity.Network -> "Conexão com a Internet Falhou"
                    is ErrorEntity.ServiceUnavailable -> "Serviço Indisponivel"
                    is ErrorEntity.Unknown -> "Falha ao Receber Dados"
                }
            }
        }
    }
}