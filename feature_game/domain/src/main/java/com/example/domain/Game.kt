package com.example.domain

import android.util.Log
import com.example.data.entity.PokemonResult
import java.util.*
import kotlin.collections.ArrayList

class Game(
    val pokemonList: ArrayList<PokemonResult>,
    val correctPoke: PokemonResult
) {

    fun checkResult(result: String): Boolean {
        return result.lowercase(Locale.ROOT) == correctPoke.name.lowercase(Locale.ROOT)
    }

}