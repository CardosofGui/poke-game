package com.example.data.implementation

import com.example.data.entity.PokemonsApiResult
import com.example.data.errorhandler.ResultsGame
import io.ktor.client.*
import io.ktor.client.request.*


class PokemonImplementation(private val client : HttpClient, private val generalErrorHandlerGameImplementation: GeneralErrorHandlerGameGameImplementation) {

    suspend fun getAllPokemon(): ResultsGame<PokemonsApiResult> {
        return try {
            val response : PokemonsApiResult = client.get(path = "api/v2/pokemon?limit=389")

            ResultsGame.Sucess(response)
        } catch (t : Throwable) {
            ResultsGame.Error(generalErrorHandlerGameImplementation.getError(t))
        }
    }
}