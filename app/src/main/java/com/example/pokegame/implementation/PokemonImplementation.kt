package com.example.pokegame.implementation

import com.example.pokegame.data.PokemonInterface
import com.example.pokegame.domain.PokemonsApiResult

class PokemonImplementation(private val pokemonInterface: PokemonInterface) {

    fun getAllPokemon(): PokemonsApiResult? {
        val callback = pokemonInterface.getAllPokemonList().execute()

        return if (callback.isSuccessful) callback.body()
        else null
    }

}