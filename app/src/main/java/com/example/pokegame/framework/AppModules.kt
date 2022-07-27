package com.example.pokegame.framework

import com.example.pokegame.data.PokemonInterface
import com.example.pokegame.data.PokemonRepository
import com.example.pokegame.data.RecordInterface
import com.example.pokegame.data.RecordRepository
import com.example.pokegame.framework.viewmodel.GameViewModel
import com.example.pokegame.framework.viewmodel.RecordViewModel
import com.example.pokegame.implementation.PokemonImplementation
import com.example.pokegame.implementation.RecordImplementation
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModules {

    fun loadModules() {
        loadKoinModules(viewModelModule() + postsModule() + networkService())
    }

    private fun viewModelModule() = module {
        single { GameViewModel(get()) }

        single { RecordViewModel(get()) }
    }

    private fun postsModule() = module {
        single { PokemonRepository(get(), get()) }

        single { PokemonImplementation(get()) }

        single { RecordRepository(get()) }

        single { RecordImplementation(get()) }
    }

    private fun networkService() = module {
        single<PokemonInterface> { createService(POKEAPI_BASE) }

        single<RecordInterface> { createService(POKEGAME_BASE) }
    }

    private inline fun <reified T> createService(
        baseURL : String
    ) : T {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }


    const val POKEAPI_BASE = "https://pokeapi.co/api/v2/"

    // TODO 01 - É NECESSARIO CRIAÇÃO DE UMA API PARA ARMAZENAR OS RECORDS DO POKEGAME, VERIFIQUE O SQL BASE NO REPOSITORIO
    const val POKEGAME_BASE = ""
}