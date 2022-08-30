package com.example.pokegame.data.implementation

import com.example.pokegame.data.entities.PokemonsApiResult
import io.ktor.client.*
import io.ktor.client.request.*

class PokemonImplementation(private val client : HttpClient) {

    suspend fun getAllPokemon(): PokemonsApiResult = client.get(path = "api/v2/pokemon?limit=386")

}