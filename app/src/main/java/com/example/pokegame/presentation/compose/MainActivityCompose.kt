package com.example.pokegame.presentation.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.core.presentation.AnimationInitialScreenTransition
import com.core.presentation.AnimationScreenTransition
import com.example.game.presentation.compose.InitialScreen
import com.example.game.presentation.viewmodel.GameViewModel
import com.example.pokegame.presentation.NavigationItem
import com.example.pokegame.presentation.ui.theme.CustomColors
import com.example.pokegame.presentation.ui.theme.PokeGameTheme
import com.example.presentation.compose.GameScreen
import com.example.record.presentation.compose.RecordsScreen
import com.example.record.presentation.viewmodel.RecordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivityCompose : ComponentActivity() {

    private val gameViewModel : GameViewModel by viewModel()
    private val recordsViewModel : RecordViewModel by viewModel()

    private var initialTransition = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            content = {
                NavHost(navController = navController, startDestination = "initial", modifier = Modifier.padding(it)) {
                    composable("initial") {
                        if(initialTransition) {
                            AnimationScreenTransition {
                                InitialScreen(navController = navController, gameViewModel = gameViewModel)
                            }
                        } else {
                            AnimationInitialScreenTransition {
                                InitialScreen(navController, gameViewModel)
                            }

                            initialTransition = true
                        }
                    }
                    composable("records") { AnimationScreenTransition { RecordsScreen(recordsViewModel) } }
                    composable("game") { AnimationInitialScreenTransition { GameScreen(gameViewModel, navController) } }
                }
            },
            contentColor = Color.White,
        )
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf(
            NavigationItem.Initial,
            NavigationItem.Records
        )

        BottomNavigation(
            backgroundColor = CustomColors.bottomNavigationColor,
            contentColor = Color.Black
        ) {
            items.forEach {item ->
                BottomNavigationItem(
                    selected = false,
                    icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.title) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Black,
                    alwaysShowLabel = true,
                    label = { Text(
                        text = item.title
                    ) },
                    onClick = {
                        navController.navigate(item.route)
                    }
                )
            }
        }
    }
}