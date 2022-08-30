package com.example.pokegame.data.implementation

import com.example.pokegame.data.entities.PokemonsApiResult
import com.example.pokegame.domain.errorhandler.Results
import io.ktor.client.*
import io.ktor.client.request.*


class PokemonImplementation(private val client : HttpClient, private val generalErrorHandlerImplementation: GeneralErrorHandlerImplementation) {

    suspend fun getAllPokemon(): Results<PokemonsApiResult> {
        return try {
            val response : PokemonsApiResult = client.get(path = "api/v2/pokemon?limit=389")

            Results.Sucess(response)
        } catch (t : Throwable) {
            Results.Error(generalErrorHandlerImplementation.getError(t))
        }
    }
}