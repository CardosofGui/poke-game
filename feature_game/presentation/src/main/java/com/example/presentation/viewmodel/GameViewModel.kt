package com.example.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.data.entity.PokemonResult
import com.example.data.entity.UserPointsModelGame
import com.example.data.errorhandler.ErrorEntityGame
import com.example.data.errorhandler.ResultsGame
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
            is ResultsGame.Sucess -> {
                _pokemonList.clear()
                _pokemonList.addAll(result.data.results)
            }
            is ResultsGame.Error -> {
                errorStatus = when(result.error)  {
                    is ErrorEntityGame.Network -> "Conexão com a Internet Falhou"
                    is ErrorEntityGame.ServiceUnavailable -> "Serviço Indisponivel"
                    is ErrorEntityGame.Unknown -> "Falha ao Receber Dados"
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

    fun insertRecord(userPoints: UserPointsModelGame) = viewModelScope.launch {
        statusInsert = when(val result = gameUseCase.insertRecordUseCase(userPoints)) {
            is ResultsGame.Sucess -> {
                "Record salvo com sucesso!"
            }
            is ResultsGame.Error -> {
                when(result.error)  {
                    is ErrorEntityGame.Network -> "Conexão com a Internet Falhou"
                    is ErrorEntityGame.ServiceUnavailable -> "Serviço Indisponivel"
                    is ErrorEntityGame.Unknown -> "Falha ao Inserir Dados"
                }
            }
        }
    }
    fun resetInsertStatus() {
        statusInsert = ""
    }
}

