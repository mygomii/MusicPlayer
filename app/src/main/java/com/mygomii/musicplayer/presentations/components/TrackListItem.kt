package com.mygomii.musicplayer.presentations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mygomii.musicplayer.data.models.Track

@Composable
fun TrackListItem(track: Track, onTrackClick: () -> Unit) {
    val bgColor = if (track.isSelected) Color.Gray else Color.DarkGray

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = bgColor)
            .padding(all = 12.dp)
            .clickable(onClick = onTrackClick)

    ) {
        Cover(track.image, 60.dp)

        Column {
            Title(title = track.title)
            Singer(singer = track.artist)
        }
    }
}