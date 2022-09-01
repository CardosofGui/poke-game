package com.example.pokegame.presentation.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokegame.R
import com.example.pokegame.data.entities.UserPointsModel
import com.example.pokegame.presentation.ui.theme.CustomColors
import com.example.pokegame.presentation.ui.theme.CustomFonts
import com.example.pokegame.presentation.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun GameScreen(gameViewModel: GameViewModel, navController: NavController) {
    val game = gameViewModel.game

    var timerCount by remember { mutableStateOf(5) }
    var ticks by remember { mutableStateOf(0) }
    var timerStatus by remember { mutableStateOf(true) }

    var openDialog by remember { mutableStateOf(false) }

    var usernameEditText by remember { mutableStateOf("") }
    var personSelect by remember { mutableStateOf("M") }
    var teamSelect by remember { mutableStateOf("R") }

    LaunchedEffect(Unit) {
        while (timerStatus) {
            delay(1.milliseconds)

            if (ticks == 1000) {
                timerCount--
                ticks = 0
            } else ticks++
        }
    }

    if (timerCount == 0) {
        timerStatus = false
        openDialog = true
        timerCount = 5
    }

    if (openDialog) {
        Dialog(onDismissRequest = {
            openDialog = false
            navController.navigate("initial")
            gameViewModel.resetGame()
        }) {
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.new_score),
                        contentDescription = "Nova Pontuação",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )

                    Text(
                        text = "${gameViewModel.getPoints()}  Pontos",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = CustomFonts.PokeFont,
                        color = CustomColors.infoColor,
                        fontSize = 24.sp
                    )

                    OutlinedTextField(
                        value = usernameEditText,
                        onValueChange = { usernameEditText = it },
                        label = {
                            Text(
                                text = "Digite seu nome...",
                                color = CustomColors.infoColor
                            )
                        },
                        textStyle = TextStyle(
                            color = CustomColors.infoColor
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = CustomColors.infoColor,
                            unfocusedBorderColor = CustomColors.infoColor,
                            cursorColor = CustomColors.infoColor
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_supervised_user_circle_24),
                                contentDescription = "User Icon",
                                tint = CustomColors.infoColor
                            )
                        }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.choose_person),
                        contentDescription = "Escolha um Personagem",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(top = 6.dp)
                    )

                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.treinador),
                            contentDescription = "Treinador",
                            modifier = Modifier
                                .weight(1f)
                                .size(90.dp)
                                .clickable { personSelect = "M" }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.treinadora),
                            contentDescription = "Treinadora",
                            modifier = Modifier
                                .weight(1f)
                                .size(90.dp)
                                .clickable { personSelect = "F" }
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.choose_team),
                        contentDescription = "Escolha um Personagem",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(top = 6.dp)
                    )

                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.team_red),
                            contentDescription = "Team Red",
                            modifier = Modifier
                                .weight(1f)
                                .size(90.dp)
                                .clickable { teamSelect = "R" }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.team_blue),
                            contentDescription = "Team Blue",
                            modifier = Modifier
                                .weight(1f)
                                .size(90.dp)
                                .clickable { teamSelect = "B" }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.team_yellow),
                            contentDescription = "Team Yellow",
                            modifier = Modifier
                                .weight(1f)
                                .size(90.dp)
                                .clickable { teamSelect = "Y" }
                        )
                    }

                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(
                            onClick = {
                                val userPoints = UserPointsModel(
                                    null,
                                    usernameEditText,
                                    gameViewModel.getPoints().toString(),
                                    teamSelect,
                                    personSelect
                                )

                                gameViewModel.insertRecord(userPoints)
                                openDialog = false
                                navController.navigate("initial")
                                gameViewModel.resetGame()
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                backgroundColor = CustomColors.playColor
                            )
                        ) {
                            Text(
                                text = "SALVAR",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.who_is_icon),
            contentDescription = "Quem é esse Pokémon?",
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        )
        if (game != null) {
            Image(
                painter = rememberAsyncImagePainter(game.correctPoke.getImage()),
                contentDescription = "Correct Pokémon Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                colorFilter = ColorFilter.tint(CustomColors.hidePokemonColor))

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(game.pokemonList) { poke ->
                    Button(
                        onClick = {
                            if (game.checkResult(poke.name)) {
                                gameViewModel.winGame(ticks)
                                timerCount = 5
                                ticks = 0
                            } else {
                                timerCount = 5
                                timerStatus = false
                                openDialog = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = CustomColors.playColor,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = poke.name.capitalize(),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text(
                text = "$timerCount Segundos",
                color = CustomColors.timerColor,
                fontFamily = CustomFonts.PokeFont,
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            gameViewModel.createGame()
        }
    }
}