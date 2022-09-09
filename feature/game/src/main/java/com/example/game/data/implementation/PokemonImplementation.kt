package com.example.game.data.implementation

import com.core.data.errorhandler.Results
import com.example.core_android.data.implementation.GeneralErrorHandlerImplementation
import com.example.game.data.entity.PokemonsApiResult
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