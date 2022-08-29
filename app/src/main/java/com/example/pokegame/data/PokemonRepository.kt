package com.example.pokegame.data

import com.example.pokegame.domain.UserPoints
import com.example.pokegame.implementation.PokemonImplementation
import com.example.pokegame.implementation.RecordImplementation

class PokemonRepository(private val pokemonImplementation: PokemonImplementation, private val recordImplementation: RecordImplementation) {

    suspend fun getAllPokemon() = pokemonImplementation.getAllPokemon()

    suspend fun insertRecord(userPoints: UserPoints) = recordImplementation.insertRecord(userPoints)

}