package com.example.data.repository

import com.example.data.implementation.PokemonImplementation

class PokemonRepository(private val pokemonImplementation: PokemonImplementation) {
    suspend fun getAllPokemon() = pokemonImplementation.getAllPokemon()

}