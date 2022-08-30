package com.example.pokegame.domain.usecase

data class GameUseCase(
    val getAllPokemonUseCase: GetAllPokemonUseCase,
    val insertRecordUseCase: InsertRecordUseCase
)