package com.example.game.presentation.compose

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.core.data.entity.UserPointsModel
import com.core.presentation.utils.CustomColors
import com.core.presentation.utils.CustomFonts
import com.example.game.data.entity.PokemonResult
import com.example.game.R
import com.example.game.presentation.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(gameViewModel: GameViewModel, navController: NavController) {
    val game = gameViewModel.game

    var gameTimerCount by remember { mutableStateOf(5) }
    var ticks by remember { mutableStateOf(5000) }
    var timerGameStatus by remember { mutableStateOf(true) }
    var hidePokemonColor by remember { mutableStateOf<Color?>(CustomColors.hidePokemonColor) }

    var openDialog by remember { mutableStateOf(false) }

    var timerWinGame by remember { mutableStateOf(false) }
    var timerLossGame by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (timerGameStatus) {
            delay(1.milliseconds)

            ticks--

            if(ticks % 1000 == 2) {
                gameTimerCount--
            }
        }
    }

    if(timerWinGame) {
        val points = ticks

        LaunchedEffect(Unit) {
            gameTimerCount = 5 // Resetando o valor do Timer do Jogo
            ticks = 5000
            hidePokemonColor = null

            delay(500.milliseconds) // Delay 0.5 Seg. antes de trocar de jogo

            hidePokemonColor = CustomColors.hidePokemonColor
            timerWinGame = false
            gameViewModel.winGame(points)
        }
    }
    if(timerLossGame) {
        LaunchedEffect(Unit) {
            gameTimerCount = 5 // Resetando o valor do Timer do Jogo
            timerGameStatus = false
            ticks = 5000
            hidePokemonColor = null

            delay(2.seconds) // Delay 2 Seg. antes de finalizar o jogo

            timerLossGame = false
            openDialog =  true
        }
    }

    if (gameTimerCount == 0) {
        timerGameStatus = false
        openDialog = true
        gameTimerCount = 5
    }

    if (openDialog) {
        Dialog(onDismissRequest = {
            openDialog = false
            navController.navigate("initial")
            gameViewModel.resetGame()
        }) {
            CardInsertPoints(
                points = gameViewModel.getPoints(),
                submitInsert = { name, person, team ->
                    val userPoints = UserPointsModel(
                        null,
                        username = name,
                        points = gameViewModel.getPoints(),
                        team = team,
                        person = person
                    )

                    gameViewModel.insertRecord(userPoints)
                    openDialog = false
                    navController.navigate("initial")
                    gameViewModel.resetGame()
                }
            )
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
            
            AnimatedContent(
                targetState = hidePokemonColor,
                transitionSpec = {
                    if(targetState == null) {
                        slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()
                    }
                }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        game.correctPoke.getImage()
                    ),
                    contentDescription = "Imagem Pokemon Correto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    colorFilter = it?.let { it1 -> ColorFilter.tint(it1) })
            }

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(game.pokemonList) { poke ->
                    ButtonAnswer(poke = poke) {
                        if (game.checkResult(poke.name)) {
                            timerWinGame =  true
                        } else {
                            timerLossGame = true
                        }
                    }
                }
            }

            Text(
                text = "$gameTimerCount Segundos",
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

@Composable
fun ButtonAnswer(poke : PokemonResult, onClick : () -> Unit){
    Row {
        Button(
            onClick = {
                onClick()
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CardInsertPoints(
    points: String,
    submitInsert: (name : String, person : String, team : String) -> Unit
) {
    var usernameEditText by remember { mutableStateOf("") }
    var personSelect by remember { mutableStateOf("M") }
    var teamSelect by remember { mutableStateOf("R") }

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
                text = "$points  Pontos",
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

            AnimatedContent(
                targetState = personSelect,
                transitionSpec = {
                    fadeIn(tween(500, 500)) with
                            fadeOut(tween(500, 500))
                }
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.treinador),
                        contentDescription = "Treinador",
                        modifier = Modifier
                            .weight(1f)
                            .size(90.dp)
                            .clickable { personSelect = "M" },
                        colorFilter = if (personSelect == "M") null else ColorFilter.tint(
                            CustomColors.hidePokemonColor
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.treinadora),
                        contentDescription = "Treinadora",
                        modifier = Modifier
                            .weight(1f)
                            .size(90.dp)
                            .clickable { personSelect = "F" },
                        colorFilter = if (personSelect == "F") null else ColorFilter.tint(
                            CustomColors.hidePokemonColor
                        )
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.choose_team),
                contentDescription = "Escolha um Personagem",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(top = 6.dp)
            )


            AnimatedContent(
                targetState = teamSelect,
                transitionSpec = {
                    fadeIn() with
                            fadeOut()
                }
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.team_red),
                        contentDescription = "Team Red",
                        modifier = Modifier
                            .weight(1f)
                            .size(90.dp)
                            .clickable { teamSelect = "R" },
                        colorFilter = if (teamSelect == "R") null else ColorFilter.tint(
                            CustomColors.hidePokemonColor
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.team_blue),
                        contentDescription = "Team Blue",
                        modifier = Modifier
                            .weight(1f)
                            .size(90.dp)
                            .clickable { teamSelect = "B" },
                        colorFilter = if (teamSelect == "B") null else ColorFilter.tint(
                            CustomColors.hidePokemonColor
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.team_yellow),
                        contentDescription = "Team Yellow",
                        modifier = Modifier
                            .weight(1f)
                            .size(90.dp)
                            .clickable { teamSelect = "Y" },
                        colorFilter = if (teamSelect == "Y") null else ColorFilter.tint(
                            CustomColors.hidePokemonColor
                        )
                    )
                }
            }


            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {
                        submitInsert(
                            usernameEditText,
                            personSelect,
                            teamSelect
                        )
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