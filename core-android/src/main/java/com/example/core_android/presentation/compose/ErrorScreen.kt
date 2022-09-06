package com.example.core_android.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.core_android.presentation.utils.CustomColors
import com.example.core_android.presentation.utils.CustomFonts


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