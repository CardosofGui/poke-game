package com.example.pokegame.framework

import android.util.Log
import com.example.pokegame.data.PokemonInterface
import com.example.pokegame.data.PokemonRepository
import com.example.pokegame.data.RecordInterface
import com.example.pokegame.data.RecordRepository
import com.example.pokegame.framework.viewmodel.GameViewModel
import com.example.pokegame.framework.viewmodel.RecordViewModel
import com.example.pokegame.implementation.PokemonImplementation
import com.example.pokegame.implementation.RecordImplementation
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModules {

    fun loadModules() {
        loadKoinModules(viewModelModule() + postsModule())
    }

    private fun viewModelModule() = module {
        single { GameViewModel(get()) }

        single { RecordViewModel(get()) }
    }

    private fun postsModule() = module {
        single { PokemonRepository(get(), get()) }

        single { PokemonImplementation(createService(POKEAPI_BASE)) }

        single { RecordRepository(get()) }

        single { RecordImplementation(createService("")) }
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
                    followRedirects = false
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

                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                // Log para status dos requests
                onResponse {response ->
                    Log.d("HTTP Status", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                // Definindo valores padroes que serao usados em cada request
                header(HttpHeaders.ContentType, ContentType.Application.Json)
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

    // TODO 01 - É NECESSARIO CRIAÇÃO DE UMA API PARA ARMAZENAR OS RECORDS DO POKEGAME, VERIFIQUE O SQL BASE NO REPOSITORIO
    const val POKEGAME_BASE = ""

    private const val TIME_OUT = 60_000
}