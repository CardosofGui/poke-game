package com.example.pokegame.presentation.compose

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ComponentRegistry
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.pokegame.R
import com.example.pokegame.presentation.ui.theme.CustomColors
import com.example.pokegame.presentation.ui.theme.CustomFonts
import com.example.pokegame.presentation.viewmodel.GameViewModel

@Composable
fun InitialScreen(navController: NavController?, gameViewModel: GameViewModel) {
    gameViewModel.getAllPokemon()

    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
            .padding(16.dp)
    ) {
        if(openDialog.value) {
            Dialog(onDismissRequest = { openDialog.value = false }) {
                CardDialog()
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
            text = "Seja bem vindo(a) treinad(a) Pokémon! Está pronto para por em prática seus conhecimentos do fantástico mundo Pokémon?",
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
                    text = "COMO JOGAR?",
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    if(gameViewModel.pokemonList.value?.results?.isNotEmpty() == true) {
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
}

@Composable
fun CardDialog() {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components(fun ComponentRegistry.Builder.() {}).build()

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
            Image(painter = painterResource(id = R.drawable.info_text), contentDescription = "Pokegame Info")

            Text(
                text = "PokeGame é um jogo simples para por em prática os seus conhecimentos do mundo Pokémon através da brincadeira mais conhecida desse mundo, a \"Quem é esse Pokémon?\". \n\nNesse Quiz você terá 1 Foto de um Pokémon em preto e branco e 4 opções, e através dos traços da foto você deve adivinhar qual é o Pokémon.\n\nVocê terá 5 segundos para adivinhar e quão mais rapído e mais longe chegar acertando os Pokémons mais pontos acumulará o que ao final da partida poderá salvar seu Record em nosso banco de dados!",
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFonts.Alata
            )

            Text(
                text = "• Demonstração",
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFonts.Alata,
                fontSize = 22.sp,
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

@Preview
@Composable
fun CardDialogPreview() {
    CardDialog()
}