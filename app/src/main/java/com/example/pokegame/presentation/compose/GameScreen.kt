package com.example.pokegame.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pokegame.R
import com.example.pokegame.data.entities.PokemonResult
import com.example.pokegame.data.entities.PokemonsApiResult
import com.example.pokegame.domain.Game
import com.example.pokegame.presentation.ui.theme.CustomColors
import com.example.pokegame.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Image(
            painter = painterResource(id = R.drawable.who_is_icon),
            contentDescription = "Quem é esse Pokémon?",
            modifier = Modifier.height(120.dp).fillMaxWidth()
        )

        if (gameViewModel.game != null) {

            Image(
                painter = rememberAsyncImagePainter(gameViewModel.game!!.correctPoke.getImage()),
                contentDescription = "Correct Pokémon Image",
                modifier = Modifier.fillMaxWidth().height(160.dp),
                colorFilter = ColorFilter.tint(CustomColors.hidePokemonColor)
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        if (gameViewModel.game!!.checkResult(gameViewModel.game!!.pokemonList[0].name)) gameViewModel.winGame(
                            0
                        )
                    },
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = CustomColors.playColor,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = gameViewModel.game!!.pokemonList[0].name.capitalize(),
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = {
                        if (gameViewModel.game!!.checkResult(gameViewModel.game!!.pokemonList[1].name)) gameViewModel.winGame(
                            0
                        )
                    },
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = CustomColors.playColor,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = gameViewModel.game!!.pokemonList[1].name.capitalize(),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        if (gameViewModel.game!!.checkResult(gameViewModel.game!!.pokemonList[2].name)) gameViewModel.winGame(
                            0
                        )
                    },
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = CustomColors.playColor,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = gameViewModel.game!!.pokemonList[2].name.capitalize(),
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = {
                        if (gameViewModel.game!!.checkResult(gameViewModel.game!!.pokemonList[3].name)) gameViewModel.winGame(
                            0
                        )
                    },
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = CustomColors.playColor,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = gameViewModel.game!!.pokemonList[3].name.capitalize(),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            gameViewModel.createGame()
        }
    }
}