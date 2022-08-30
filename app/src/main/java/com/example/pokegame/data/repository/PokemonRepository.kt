package com.example.pokegame.data.repository

import com.example.pokegame.data.implementation.PokemonImplementation

class PokemonRepository(private val pokemonImplementation: PokemonImplementation) {
    suspend fun getAllPokemon() = pokemonImplementation.getAllPokemon()

}