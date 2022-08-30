package com.example.pokegame.usecase

import com.example.pokegame.data.PokemonRepository
import com.example.pokegame.implementation.PokemonImplementation

class GetAllPokemonUseCase(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke() = pokemonRepository.getAllPokemon()

}