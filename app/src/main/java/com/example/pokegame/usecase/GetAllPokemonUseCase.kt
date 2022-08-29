package com.example.pokegame.usecase

import com.example.pokegame.implementation.PokemonImplementation

class GetAllPokemonUseCase(private val pokemonImplementation: PokemonImplementation) {

    suspend operator fun invoke() = pokemonImplementation.getAllPokemon()

}