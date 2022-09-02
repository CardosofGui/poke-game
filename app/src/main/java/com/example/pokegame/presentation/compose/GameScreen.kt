package com.example.pokegame.presentation.compose

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.pokegame.R
import com.example.pokegame.data.entities.PokemonResult
import com.example.pokegame.data.entities.UserPointsModel
import com.example.pokegame.domain.Game
import com.example.pokegame.presentation.ui.theme.CustomColors
import com.example.pokegame.presentation.ui.theme.CustomFonts
import com.example.pokegame.presentation.viewmodel.GameViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

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
        LaunchedEffect(Unit) {
            gameTimerCount = 5 // Resetando o valor do Timer do Jogo
            hidePokemonColor = null

            delay(500.milliseconds) // Delay 1 Seg. antes de trocar de jogo

            hidePokemonColor = CustomColors.hidePokemonColor
            gameViewModel.winGame(ticks)
            ticks = 5000
            timerWinGame = false
        }
    }
    if(timerLossGame) {
        LaunchedEffect(Unit) {
            gameTimerCount = 5 // Resetando o valor do Timer do Jogo
            timerGameStatus = false
            hidePokemonColor = null
            ticks = 5000

            delay(2.seconds) // Delay 2 Seg. antes de trocar de jogo

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
            Image(
                painter = rememberAsyncImagePainter(
                    game.correctPoke.getImage()
                ),
                contentDescription = "Imagem Pokemon Correto",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                colorFilter = hidePokemonColor?.let { ColorFilter.tint(it) })

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