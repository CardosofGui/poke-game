package com.example.domain.usecase

import com.example.data.repository.PokemonRepository


class GetAllPokemonUseCase(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke() = pokemonRepository.getAllPokemon()

}