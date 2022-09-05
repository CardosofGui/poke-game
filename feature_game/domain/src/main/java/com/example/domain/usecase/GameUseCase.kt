package com.example.domain.usecase

data class GameUseCase(
    val getAllPokemonUseCase: GetAllPokemonUseCase,
    val insertRecordUseCase: InsertRecordUseCase
)