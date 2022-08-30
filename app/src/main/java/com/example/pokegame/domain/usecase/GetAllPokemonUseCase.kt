package com.example.pokegame.domain.usecase

import com.example.pokegame.data.repository.PokemonRepository

class GetAllPokemonUseCase(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke() = pokemonRepository.getAllPokemon()

}