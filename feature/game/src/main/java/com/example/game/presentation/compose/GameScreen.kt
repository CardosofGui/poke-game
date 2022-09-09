package com.example.game.presentation.compose

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.core_android.data.entity.UserPointsModel
import com.example.core_android.presentation.utils.CustomColors
import com.example.core_android.presentation.utils.CustomFonts
import com.example.game.data.entity.PokemonResult
import com.example.game.R
import com.example.game.domain.Game
import com.example.game.presentation.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
@Composable
fun GameScreen(gameViewModel: GameViewModel, navController: NavController) {
    val game = gameViewModel.game

    var gameTimerCount by remember { mutableStateOf(5) }
    var ticks by remember { mutableStateOf(5000) }
    var timerGameStatus by remember { mutableStateOf(true) }

    var openDialog by remember { mutableStateOf(false) }

    var timerWinGame by remember { mutableStateOf(false) }
    var timerLossGame by remember { mutableStateOf(false) }

    var hidePokemonColor by remember { mutableStateOf<Color?>(CustomColors.hidePokemonColor) }

    var buttonClickable by remember { mutableStateOf(true) }

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
            buttonClickable = false
            gameTimerCount = 5 // Resetando o valor do Timer do Jogo
            ticks = 5000
            hidePokemonColor = null

            delay(500.milliseconds) // Delay 0.5 Seg. antes de trocar de jogo

            buttonClickable =  true
            hidePokemonColor = CustomColors.hidePokemonColor
            timerWinGame = false
            gameViewModel.winGame(points)
        }
    }
    if(timerLossGame) {
        LaunchedEffect(Unit) {
            buttonClickable = false
            gameTimerCount = 5 // Resetando o valor do Timer do Jogo
            timerGameStatus = false
            ticks = 5000
            hidePokemonColor = null

            delay(2.seconds) // Delay 2 Seg. antes de finalizar o jogo

            buttonClickable = true
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp),
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
            
            ImagePokemonHide(
                game = game, hidePokemonColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(game.pokemonList) { poke ->
                    ButtonAnswer(
                        poke = poke,
                        enabled = buttonClickable,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)
                    ) {
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
fun ButtonAnswer(
    poke : PokemonResult,
    enabled : Boolean,
    modifier: Modifier = Modifier,
    onClick : () -> Unit
){
    Row {
        Button(
            onClick = {
                onClick()
            },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = CustomColors.playColor,
                contentColor = Color.Black
            ),
            enabled = enabled
        ) {
            Text(
                text = poke.name.replaceFirstChar { it.uppercase() },
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CardInsertPoints(
    points: String,
    submitInsert: (name : String, person : String, team : String) -> Unit,
    modifier: Modifier = Modifier
) {
    var usernameEditText by remember { mutableStateOf("") }
    var personSelect by remember { mutableStateOf("M") }
    var teamSelect by remember { mutableStateOf("R") }

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
        modifier = modifier
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
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    ImageChoose(
                        itemSelect = personSelect,
                        actionClick = { personSelect = "M" },
                        confirm = "M",
                        imageGender = R.drawable.treinador,
                        Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                    )

                    ImageChoose(
                        itemSelect = personSelect,
                        actionClick = { personSelect = "F" },
                        confirm = "F",
                        imageGender = R.drawable.treinadora,
                        Modifier
                            .size(90.dp)
                            .clip(CircleShape)
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
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    ImageChoose(
                        itemSelect = teamSelect,
                        actionClick = { teamSelect = "R" },
                        confirm = "R",
                        imageGender = R.drawable.team_red,
                        Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                    )

                    ImageChoose(
                        itemSelect = teamSelect,
                        actionClick = { teamSelect = "B" },
                        confirm = "B",
                        imageGender = R.drawable.team_blue,
                        Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                    )

                    ImageChoose(
                        itemSelect = teamSelect,
                        actionClick = { teamSelect = "Y" },
                        confirm = "Y",
                        imageGender = R.drawable.team_yellow,
                        Modifier
                            .size(90.dp)
                            .clip(CircleShape)
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

@Composable
fun ImageChoose(
    itemSelect : String,
    actionClick : (String) -> Unit,
    confirm : String,
    imageGender : Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imageGender),
        contentDescription = "Treinadora",
        modifier = modifier
            .clickable { actionClick(confirm) },
        colorFilter = if (itemSelect == confirm) null else ColorFilter.tint(
            CustomColors.hidePokemonColor
        )
    )

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ImagePokemonHide(
    game : Game,
    hidePokemonColor : Color?,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = hidePokemonColor,
        transitionSpec = {
            slideInVertically { height -> -height } + fadeIn() with
                    slideOutVertically { height -> height } + fadeOut()
        }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                game.correctPoke.getImage()
            ),
            contentDescription = "Imagem Pokemon Correto",
            modifier = modifier,
            colorFilter = it?.let { it1 -> ColorFilter.tint(it1) })
    }
}