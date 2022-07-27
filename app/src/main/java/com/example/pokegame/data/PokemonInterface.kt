package com.example.pokegame.data

import com.example.pokegame.domain.PokemonResult
import com.example.pokegame.domain.PokemonsApiResult
import retrofit2.Call
import retrofit2.http.GET
import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST

interface PokemonInterface {

    @GET("pokemon?limit=386")
    fun getAllPokemonList() : Call<PokemonsApiResult>

}