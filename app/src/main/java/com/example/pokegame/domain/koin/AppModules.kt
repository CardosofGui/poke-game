package com.example.pokegame.domain.koin

import android.util.Log
import com.example.pokegame.data.implementation.GeneralErrorHandlerImplementation
import com.example.pokegame.data.repository.PokemonRepository
import com.example.pokegame.data.repository.RecordRepository
import com.example.pokegame.presentation.viewmodel.GameViewModel
import com.example.pokegame.presentation.viewmodel.RecordViewModel
import com.example.pokegame.data.implementation.PokemonImplementation
import com.example.pokegame.data.implementation.RecordImplementation
import com.example.pokegame.domain.usecase.GameUseCase
import com.example.pokegame.domain.usecase.GetAllPokemonUseCase
import com.example.pokegame.domain.usecase.GetAllRecordsUseCase
import com.example.pokegame.domain.usecase.InsertRecordUseCase
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    fun getModules() : List<Module> = listOf(
        viewModelModule(),
        repositoryModule(),
        useCaseModule(),
    )

    private fun viewModelModule() = module {
        viewModel { GameViewModel(get()) }

        viewModel { RecordViewModel(get()) }
    }

    private fun repositoryModule() = module {
        factory { PokemonRepository(get()) }

        single { PokemonImplementation(createService(POKEAPI_BASE), get()) }

        factory { RecordRepository(get()) }

        single { RecordImplementation(createService(POKEGAME_BASE), get()) }

        single { GeneralErrorHandlerImplementation() }
    }

    private fun useCaseModule() = module {
        factory { GameUseCase(get(), get()) }

        factory { InsertRecordUseCase(get()) }

        factory { GetAllPokemonUseCase(get()) }

        factory { GetAllRecordsUseCase(get()) }
    }

    private fun createService(
        baseURL : String
    ): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                // Configurando o Serializador e Deserializador
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

                // Configurando TimeOUT
                engine {
                    connectTimeout = TIME_OUT
                    socketTimeout = TIME_OUT
                }
            }

            install(Logging) {
                // Log para todos requests utilizando Ktor
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Log Ktor", message)
                    }
                }

                level = LogLevel.NONE
            }

            install(ResponseObserver) {
                // Log para status dos requests
                onResponse {response ->
                    Log.d("HTTP Status", "${response.status.value}")
                }
            }

            defaultRequest {
                url(
                    scheme = "https",
                    host = baseURL,
                    path = this.url.encodedPath
                )
            }
        }
    }


    const val POKEAPI_BASE = "pokeapi.co"
    const val POKEGAME_BASE = "pokegame-api-venture.herokuapp.com"

    private const val TIME_OUT = 10_000
}