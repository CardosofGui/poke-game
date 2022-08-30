package com.example.pokegame.data

import com.example.pokegame.domain.UserPoints
import com.example.pokegame.implementation.PokemonImplementation
import com.example.pokegame.implementation.RecordImplementation

class PokemonRepository(private val pokemonImplementation: PokemonImplementation) {
    suspend fun getAllPokemon() = pokemonImplementation.getAllPokemon()

}