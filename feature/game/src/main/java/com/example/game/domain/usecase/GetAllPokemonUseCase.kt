package com.example.game.domain.usecase

import com.example.game.data.repository.PokemonRepository


class GetAllPokemonUseCase(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke() = pokemonRepository.getAllPokemon()

}