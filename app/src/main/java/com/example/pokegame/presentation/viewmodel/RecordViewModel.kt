package com.example.pokegame.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokegame.data.entities.ErrorEntitity
import com.example.pokegame.data.entities.UserPointsModel
import com.example.pokegame.domain.errorhandler.Results
import com.example.pokegame.domain.usecase.GetAllRecordsUseCase
import kotlinx.coroutines.launch

class RecordViewModel(private val getAllRecordsUseCase: GetAllRecordsUseCase) : ViewModel() {

    private val _allRecords = mutableStateListOf<UserPointsModel>()
    val allRecords : List<UserPointsModel>
        get() = _allRecords

    private val _error = MutableLiveData<String?>()
    val error : MutableLiveData<String?>
        get() = _error

    init {
        getAllRecords()
    }

    fun getAllRecords() = viewModelScope.launch {
        when(val result = getAllRecordsUseCase.invoke()) {
            is Results.Sucess -> {
                _allRecords.clear()
                _allRecords.addAll(result.data)
            }
            is Results.Error -> {
                when(result.error)  {
                    is ErrorEntitity.Network -> _error.value = "Conexão com a Internet Falhou"
                    is ErrorEntitity.ServiceUnavailable -> _error.value = "Serviço Indisponivel"
                    is ErrorEntitity.Unknown -> _error.value = "Falha ao Receber Dados"
                }
            }
        }
    }
}