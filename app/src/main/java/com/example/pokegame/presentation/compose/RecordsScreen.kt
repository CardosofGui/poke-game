package com.example.pokegame.presentation.compose

import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import coil.ComponentRegistry
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.pokegame.R
import com.example.pokegame.data.entities.UserPointsModel
import com.example.pokegame.presentation.ui.theme.CustomColors
import com.example.pokegame.presentation.ui.theme.CustomFonts
import com.example.pokegame.presentation.viewmodel.RecordViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch
import okhttp3.internal.wait


@Composable
fun RecordsScreen(recordViewModel: RecordViewModel) {
    val context = LocalContext.current
    val recordsList = remember { mutableStateOf(recordViewModel.allRecords) }
    val errorStatus = recordViewModel.errorStatus

    LaunchedEffect(Unit) {
        recordViewModel.getAllRecords()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Image(
            painter = painterResource(id = R.drawable.recordes_icon),
            contentDescription = "Records Label",
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        )

        if(recordsList.value.isNotEmpty()) {
            LazyColumn {
                items(recordsList.value) {
                    RecordCard(userPointsModel = it)
                }
            }
        } else if(errorStatus.isNotEmpty()) {
            ErrorStatus(errorStatus) {
                Toast.makeText(context, errorStatus, Toast.LENGTH_SHORT).show()
                recordViewModel.errorStatus = ""
            }
        } else {
            LoadingRecords()
        }
    }
}

@Composable
fun RecordCard(userPointsModel: UserPointsModel) {
    val userImage = if(userPointsModel.person == "M") R.drawable.treinador else R.drawable.treinadora
    val teamImage = if(userPointsModel.team == "R") R.drawable.team_red else if(userPointsModel.team == "B") R.drawable.team_blue else R.drawable.team_yellow
    val backgroundTeamColor = if(userPointsModel.team == "R") CustomColors.teamRedColor else if(userPointsModel.team == "B") CustomColors.teamBlueColor else CustomColors.teamYellowColor

    Card(
        backgroundColor = backgroundTeamColor,
        modifier = Modifier.padding(8.dp)
    ) {
        Row() {
            Image(painter = painterResource(id = userImage), contentDescription = "User Image", modifier = Modifier
                .size(80.dp)
                .weight(1f))

            Column(modifier = Modifier
                .weight(3f)
                .fillMaxWidth()) {

                Text(text = userPointsModel.username, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                Text(text = "${userPointsModel.points} Pontos", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
            }

            Image(painter = painterResource(id = teamImage), contentDescription = "Team Image", modifier = Modifier
                .size(80.dp)
                .weight(1f))
        }
    }
}
@Composable
@Preview
fun RecordCardPreview() {
    RecordCard(userPointsModel = UserPointsModel(null, "Gui", "123", "B", "M"))
}

@Composable
fun LoadingRecords() {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.loading_text_records),
                contentDescription = "Carregando Records",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }
    }
}
@Composable
@Preview
fun LoadingRecordsPreview() {
    LoadingRecords()
}

@Composable
fun ErrorStatus(error : String, reload : () -> Unit) {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = error,
            fontFamily = CustomFonts.Alata,
            fontSize = 24.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { reload() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = CustomColors.infoColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Recarregar",
                )
            }
        }
    }
}
@Composable
@Preview
fun ErrorStatusPreview() {
    ErrorStatus("Exemplo de Erro") {}
}