package com.example.game.data.repository

import com.example.game.data.implementation.PokemonImplementation

class PokemonRepository(private val pokemonImplementation: PokemonImplementation) {
    suspend fun getAllPokemon() = pokemonImplementation.getAllPokemon()

}