package com.mygomii.musicplayer.presentations.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygomii.musicplayer.Player
import com.mygomii.musicplayer.data.models.Track
import com.mygomii.musicplayer.data.models.getImage
import com.mygomii.musicplayer.presentations.components.NextIcon
import com.mygomii.musicplayer.presentations.components.PlayPauseIcon
import com.mygomii.musicplayer.presentations.components.PreviousIcon
import com.mygomii.musicplayer.ui.theme.Purple80
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

@Composable
fun DetailScreen(track: Track) {
    Player.setUpTrack(track.id, true)
    TrackView(track)
}


@Composable
fun TrackView(
    selectedTrack: Track,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        TrackInfo(
            trackImage = selectedTrack.image,
            trackName = selectedTrack.title,
            artistName = selectedTrack.artist
        )

//        TrackProgressSlider(playbackState = playbackState) {
////            playerEvents.onSeekBarPositionChanged(it)
//        }

//        TrackControls(
//            selectedTrack = selectedTrack,
//            onPreviousClick = playerEvents::onPreviousClick,
//            onPlayPauseClick = playerEvents::onPlayPauseClick,
//            onNextClick = playerEvents::onNextClick
//        )
    }
}

@Composable
fun TrackInfo(trackImage: String, trackName: String, artistName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 350.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center

    ) {
        Image(
            painter = painterResource(id = trackImage.getImage(LocalContext.current)),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x26000000),
                    ambientColor = Color(0x26000000)
                )
                .padding(top = 24.dp)
                .clip(shape = RoundedCornerShape(size = 50.dp))
                .width(360.dp)
                .height(360.dp)
        )

    }
    Text(
        color = Color.White,
        text = trackName,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )

    Text(
        color = Color.LightGray,
        text = artistName,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    )
}


@Composable
fun TrackProgressSlider(
    playbackState: StateFlow<PlaybackState>,
    onSeekBarPositionChanged: (Long) -> Unit,
) {
    val playbackStateValue = playbackState.collectAsState(
        initial = PlaybackState(0L, 0L)
    ).value

    var currentMediaProgress = Player.currentPlaybackPosition.toFloat()
//    var currentMediaProgress = playbackStateValue.currentPlaybackPosition.toFloat()
    var currentPosTemp by rememberSaveable { mutableFloatStateOf(0f) }

    Slider(
        value = if (currentPosTemp == 0f) currentMediaProgress else currentPosTemp,
        onValueChange = { currentPosTemp = it },
        onValueChangeFinished = {
            currentMediaProgress = currentPosTemp
            currentPosTemp = 0f
            onSeekBarPositionChanged(currentMediaProgress.toLong())
        },
//        valueRange = 0f..playbackStateValue.currentTrackDuration.toFloat(),
        valueRange = 0f..playbackStateValue.currentTrackDuration.toFloat(),
        colors = SliderDefaults.colors(
            activeTickColor = Purple80,
            inactiveTickColor = Color.Transparent,
            inactiveTrackColor = Color.LightGray,
            activeTrackColor = Purple80,
            thumbColor = Purple80
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = playbackStateValue.currentPlaybackPosition.formatTime(),
            fontSize = 14.sp,
            color = Color.White

        )
        Text(
            text = playbackStateValue.currentTrackDuration.formatTime(),
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun TrackControls(
    selectedTrack: Track,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PreviousIcon(onClick = onPreviousClick)

        PlayPauseIcon(
            selectedTrack = selectedTrack,
            onClick = onPlayPauseClick,
        )
        NextIcon(onClick = onNextClick)
    }
}


data class PlaybackState(
    val currentPlaybackPosition: Long,
    val currentTrackDuration: Long,
)

fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds)
}