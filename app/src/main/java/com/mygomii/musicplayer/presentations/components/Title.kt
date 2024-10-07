package com.mygomii.musicplayer.presentations.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Title(title: String, fontSize: TextUnit = 20.sp) {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
        text = title,
        fontSize = fontSize,
        color = Color.White
    )
}

@Composable
fun Singer(singer: String, fontSize: TextUnit = 18.sp) {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
        text = singer,
        fontSize = fontSize,
        color = Color.White
    )
}
