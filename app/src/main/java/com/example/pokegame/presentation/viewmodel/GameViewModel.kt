package com.example.pokegame.presentation.viewmodel

import androidx.lifecycle.*
import com.example.pokegame.data.entities.ErrorEntitity
import com.example.pokegame.domain.Game
import com.example.pokegame.data.entities.PokemonsApiResult
import com.example.pokegame.data.entities.UserPointsModel
import com.example.pokegame.domain.errorhandler.Results
import com.example.pokegame.domain.usecase.GameUseCase
import kotlinx.coroutines.launch

class GameViewModel(private val gameUseCase: GameUseCase) : ViewModel() {


    private val _error = MutableLiveData<String?>()
    val error : MutableLiveData<String?>
        get() = _error

    private val _pokemonList = MutableLiveData<PokemonsApiResult>()
    val pokemonList : MutableLiveData<PokemonsApiResult>
        get() = _pokemonList

    private var listGames = arrayListOf<Game>()
    private var pointsList = arrayListOf<Int>()

    var round : MutableLiveData<Int> = MutableLiveData<Int>(0)

    fun getAllPokemon() = viewModelScope.launch {
        when(val result = gameUseCase.getAllPokemonUseCase.invoke()) {
            is Results.Sucess -> {
                _pokemonList.postValue(result.data)
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

    fun createGame()  {
        val pokemonDataListFiltered = pokemonList.value?.results?.shuffled()?.take(4)

        listGames.add(Game(
            arrayListOf(pokemonDataListFiltered!![0], pokemonDataListFiltered[1], pokemonDataListFiltered[2], pokemonDataListFiltered[3]),
            pokemonDataListFiltered!!.shuffled()[1]
        ))
    }
    fun getActualGame() = listGames[round.value!!]

    fun winGame(timer: Int){
        pointsList.add(timer/100 + (timer/100 * round.value!!/50))
        createGame()

        if (round.value!!+1 < listGames.size) round.value = round.value?.plus(1)
        else {
            round.value = -1
            resetGame()
        }
    }
    fun lossGame(){
        round.value = -1
    }
    fun resetGame() {
        round.value = 0
        listGames = arrayListOf()
        pointsList = arrayListOf()
    }
    fun getPoints() = pointsList.sum()

    fun insertRecord(userPoints: UserPointsModel) = viewModelScope.launch {

        when(val result = gameUseCase.insertRecordUseCase(userPoints)) {
            is Results.Error -> {
                when(result.error)  {
                    is ErrorEntitity.Network -> _error.value = "Conexão com a Internet Falhou"
                    is ErrorEntitity.ServiceUnavailable -> _error.value = "Serviço Indisponivel"
                    is ErrorEntitity.Unknown -> _error.value = "Falha ao Inserir Dados"
                }
            }
        }
    }
}

