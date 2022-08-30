package com.example.pokegame.usecase

data class GameUseCase(
    val getAllPokemonUseCase: GetAllPokemonUseCase,
    val insertRecordUseCase: InsertRecordUseCase
)