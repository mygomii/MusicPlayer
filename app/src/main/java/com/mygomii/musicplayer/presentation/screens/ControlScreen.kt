package com.mygomii.musicplayer.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygomii.musicplayer.R
import com.mygomii.musicplayer.domain.models.Track
import com.mygomii.musicplayer.domain.models.getImage

@Composable
fun ControlScreen(
    track: Track,
    isPlaying: Boolean,
    playbackPosition: Long,
    duration: Long,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .size(50.dp)
                .padding(12.dp)
                .align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = Color.White,
            )
        }

        TrackImage(imageRes = track.image.getImage(context))
        TrackInfo(title = track.title, artist = track.artist)
        PlayPauseButton(isPlaying = isPlaying, onPlayPause = onPlayPause)

        PlaybackSlider(
            playbackPosition = playbackPosition,
            duration = duration,
            onSeek = onSeek
        )

        TimeDisplay(
            playbackPosition = playbackPosition,
            duration = duration
        )
    }
}


@Composable
fun TrackImage(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(top = 24.dp)
            .size(360.dp)
            .clip(RoundedCornerShape(50.dp))
    )
}

@Composable
fun TrackInfo(title: String, artist: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        color = Color.White,
        modifier = Modifier.padding(top = 16.dp)
    )
    Text(
        text = artist,
        fontSize = 16.sp,
        color = Color.LightGray,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun PlayPauseButton(isPlaying: Boolean, onPlayPause: () -> Unit) {
    IconButton(
        onClick = onPlayPause,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            painter = painterResource(
                id = if (isPlaying) R.drawable.pause else R.drawable.play
            ),
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun PlaybackSlider(
    playbackPosition: Long,
    duration: Long,
    onSeek: (Long) -> Unit
) {
    Slider(
        value = playbackPosition.coerceAtLeast(0L).toFloat(),
        onValueChange = { newPosition ->
            onSeek(newPosition.toLong())
        },
        valueRange = 0f..duration.coerceAtLeast(0L).toFloat(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )
}

@Composable
fun TimeDisplay(playbackPosition: Long, duration: Long) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = playbackPosition.formatTime(), color = Color.White)
        Text(text = duration.formatTime(), color = Color.White)
    }
}

fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}
