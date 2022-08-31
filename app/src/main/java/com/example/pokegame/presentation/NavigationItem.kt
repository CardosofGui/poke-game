package com.example.pokegame.presentation

import com.example.pokegame.R

sealed class NavigationItem(var route : String, var icon: Int, var title : String) {

    object Initial : NavigationItem("initial", R.drawable.ic_baseline_videogame_asset_24, "Jogar")
    object Records : NavigationItem("records", R.drawable.ic_baseline_leaderboard_24, "Records")

}

