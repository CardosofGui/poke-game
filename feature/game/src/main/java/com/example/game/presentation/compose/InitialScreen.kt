package com.example.game.presentation.compose

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.ComponentRegistry
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.core_android.presentation.compose.ErrorStatus
import com.example.core_android.presentation.utils.CustomColors
import com.example.core_android.presentation.utils.CustomFonts
import com.example.core_android.presentation.utils.InitialScreenTexts
import com.example.game.presentation.viewmodel.GameViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import com.example.game.R

@Composable
fun InitialScreen(navController: NavController?, gameViewModel: GameViewModel) {
    val errorStatus = gameViewModel.errorStatus
    val statusInsert = gameViewModel.statusInsert
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    if(statusInsert.isNotEmpty()) {
        FancyToast.makeText(context, statusInsert, FancyToast.INFO, FancyToast.LENGTH_SHORT, false).show()
        gameViewModel.resetInsertStatus()
    }

    if(gameViewModel.pokemonList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .wrapContentSize(Alignment.Center)
                .padding(16.dp)
        ) {
            if(openDialog.value) {
                Dialog(onDismissRequest = { openDialog.value = false }) {
                    CardDialog(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 20.dp)
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "APP Logo",
                Modifier
                    .fillMaxWidth()
                    .height(126.dp)
            )

            Text(
                text = InitialScreenTexts.WELCOME_LABEL,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = CustomFonts.Alata
            )

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = { openDialog.value = true },
                    Modifier
                        .weight(1f)
                        .padding(end = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = CustomColors.infoColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = InitialScreenTexts.HOW_PLAY,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        if(gameViewModel.pokemonList.isNotEmpty()) {
                            navController?.navigate("game")
                        }
                    },
                    Modifier
                        .weight(1f)
                        .padding(end = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = CustomColors.playColor,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "JOGAR",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    } else if(errorStatus.isNotEmpty()) {
        ErrorStatus(error = errorStatus) {
            Toast.makeText(context, errorStatus, Toast.LENGTH_SHORT).show()
            gameViewModel.errorStatus = ""
        }
    } else {
        gameViewModel.getAllPokemon()
        LoadingPokemon()
    }
}

@Composable
fun CardDialog(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context).components(fun ComponentRegistry.Builder.() {}).build()

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
            Image(painter = painterResource(id = R.drawable.info_text), contentDescription = "Pokegame Info")

            Text(
                text = InitialScreenTexts.INSTRUCTION,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFonts.Alata,
                color = Color.Black
            )

            Text(
                text = InitialScreenTexts.DEMO,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFonts.Alata,
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
            )

            Image(painter = rememberImagePainter(
                imageLoader = imageLoader,
                data = R.drawable.info
            ), contentDescription = "Gif Info",
                Modifier
                    .fillMaxWidth()
                    .height(500.dp))
        }
    }
}

@Composable
fun LoadingPokemon() {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column() {
            Image(
                painter = painterResource(id = R.drawable.loading_text),
                contentDescription = "Carregando Pokémon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }
    }
}