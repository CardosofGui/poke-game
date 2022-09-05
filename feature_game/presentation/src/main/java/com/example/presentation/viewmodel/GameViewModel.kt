package com.example.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.core.data.entity.UserPointsModel
import com.core.data.errorhandler.ErrorEntity
import com.core.data.errorhandler.Results
import com.example.data.entity.PokemonResult
import com.example.domain.Game
import com.example.domain.usecase.GameUseCase
import kotlinx.coroutines.launch

class GameViewModel(private val gameUseCase: GameUseCase) : ViewModel() {


    private var listGames = mutableListOf<Game>()
    private var pointsList = arrayListOf<Int>()

    var game : Game? by mutableStateOf(null)
    var statusInsert by mutableStateOf("")
    var errorStatus by mutableStateOf("")


    private var _pokemonList = mutableStateListOf<PokemonResult>()
    val pokemonList : List<PokemonResult>
        get() = _pokemonList

    fun getAllPokemon() = viewModelScope.launch {
        when(val result = gameUseCase.getAllPokemonUseCase.invoke()) {
            is Results.Sucess -> {
                _pokemonList.clear()
                _pokemonList.addAll(result.data.results)
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
    fun createGame()  {
        val pokemonDataListFiltered = pokemonList.shuffled()?.take(4)

        game = Game(
            arrayListOf(pokemonDataListFiltered!![0], pokemonDataListFiltered[1], pokemonDataListFiltered[2], pokemonDataListFiltered[3]),
            pokemonDataListFiltered!!.shuffled()[1]
        )
    }
    fun winGame(timer: Int){
        pointsList.add(timer)
        createGame()
    }
    fun resetGame() {
        listGames = arrayListOf()
        pointsList = arrayListOf()
    }
    fun getPoints() = pointsList.sum().toString()

    fun insertRecord(userPoints: UserPointsModel) = viewModelScope.launch {
        statusInsert = when(val result = gameUseCase.insertRecordUseCase(userPoints)) {
            is Results.Sucess -> {
                "Record salvo com sucesso!"
            }
            is Results.Error -> {
                when(result.error)  {
                    is ErrorEntity.Network -> "Conexão com a Internet Falhou"
                    is ErrorEntity.ServiceUnavailable -> "Serviço Indisponivel"
                    is ErrorEntity.Unknown -> "Falha ao Inserir Dados"
                }
            }
        }
    }
    fun resetInsertStatus() {
        statusInsert = ""
    }
}

