package com.example.game.domain.usecase

data class GameUseCase(
    val getAllPokemonUseCase: GetAllPokemonUseCase,
    val insertRecordUseCase: InsertRecordUseCase
)