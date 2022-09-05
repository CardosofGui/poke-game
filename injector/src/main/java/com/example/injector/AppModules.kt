package com.example.injector

import android.util.Log
import com.core.data.implementation.GeneralErrorHandlerImplementation
import com.example.game.data.implementation.InsertRecordImplementation
import com.example.game.data.implementation.PokemonImplementation
import com.example.game.data.repository.InsertRecordRepository
import com.example.game.data.repository.PokemonRepository
import com.example.game.domain.usecase.GameUseCase
import com.example.game.domain.usecase.GetAllPokemonUseCase
import com.example.game.domain.usecase.InsertRecordUseCase
import com.example.game.presentation.viewmodel.GameViewModel
import com.example.record.data.implementation.RecordImplementation
import com.example.record.data.repository.RecordRepository
import com.example.record.domain.usecase.GetAllRecordsUseCase
import com.example.record.presentation.viewmodel.RecordViewModel
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
        repositoryModule(),
        useCaseModule(),
        viewModelModule(),
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

        factory { InsertRecordRepository(get()) }

        single { InsertRecordImplementation(createService(POKEGAME_BASE), get()) }
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