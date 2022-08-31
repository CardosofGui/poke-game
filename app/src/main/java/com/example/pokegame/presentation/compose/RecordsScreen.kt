package com.example.pokegame.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokegame.R
import com.example.pokegame.data.entities.UserPointsModel
import com.example.pokegame.presentation.ui.theme.CustomColors
import com.example.pokegame.presentation.viewmodel.RecordViewModel


@Composable
fun RecordsScreen(recordViewModel: RecordViewModel) {
    recordViewModel.getAllRecords()
    val recordsList = remember { mutableStateOf(recordViewModel.allRecords) }

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

        LazyColumn() {
            items(recordsList.value) {
                RecordCard(userPointsModel = it)
            }
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
