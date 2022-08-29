package com.example.pokegame.framework.viewmodel

import android.widget.Toast
import androidx.lifecycle.*
import com.example.pokegame.data.PokemonRepository
import com.example.pokegame.domain.Game
import com.example.pokegame.domain.PokemonResult
import com.example.pokegame.domain.PokemonsApiResult
import com.example.pokegame.domain.UserPoints
import kotlinx.coroutines.launch

class GameViewModel(private val repository: PokemonRepository) : ViewModel() {


    private val _pokemonList = MutableLiveData<PokemonsApiResult>()
    val pokemonList : MutableLiveData<PokemonsApiResult>
        get() = _pokemonList

    private var listGames = arrayListOf<Game>()
    private var pointsList = arrayListOf<Int>()

    var round : MutableLiveData<Int> = MutableLiveData<Int>(0)

    fun getAllPokemon() = viewModelScope.launch {
        _pokemonList.postValue(repository.getAllPokemon())
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


    fun insertRecord(userPoints: UserPoints) = viewModelScope.launch {
        repository.insertRecord(userPoints)
    }
}

