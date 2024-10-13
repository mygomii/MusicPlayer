package com.mygomii.musicplayer.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygomii.musicplayer.PlayerManager
import com.mygomii.musicplayer.data.models.getTracks
import com.mygomii.musicplayer.domain.models.Track
import com.mygomii.musicplayer.domain.models.getImage

@Composable
fun MusicPlayerScreen(playerManager: PlayerManager) {
    var currentTrack by remember { mutableStateOf<Track?>(null) }
    var showPlayingView by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var playbackPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }

    LaunchedEffect(playerManager) {
        while (true) {
            if (playerManager.isPlaying) {
                playbackPosition = playerManager.currentPosition
                duration = playerManager.duration
            }
            kotlinx.coroutines.delay(500L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        TrackList(getTracks, onTrackSelected = { track ->
            println("##### currentTrack : $currentTrack")
            println("##### track : $track")

            if (currentTrack == null || currentTrack != track) {
                currentTrack = track
                showPlayingView = true
                playerManager.playTrack(track.uri)
                isPlaying = true
                return@TrackList
            }

            if (currentTrack == track) {
                showPlayingView = true
            }
        })
    }

    if (currentTrack != null && showPlayingView) {
        ControlScreen(
            track = currentTrack!!,
            isPlaying = isPlaying,
            playbackPosition = playbackPosition,
            duration = duration,
            onPlayPause = {
                if (playerManager.isPlaying) {
                    playerManager.pause()
                    isPlaying = false
                } else {
                    playerManager.play()
                    isPlaying = true
                }
            },
            onSeek = { position ->
                playerManager.seekTo(position)
                playbackPosition = position
            },
            onClose = { showPlayingView = false }
        )
    }
}


@Composable
fun TrackList(tracks: List<Track>, onTrackSelected: (Track) -> Unit) {
    LazyColumn(
        modifier = Modifier.background(Color.Black),
        contentPadding = PaddingValues(5.dp)
    ) {
        items(tracks.size) { index ->
            TrackItem(track = tracks[index], onClick = { onTrackSelected(tracks[index]) })
        }
    }
}

@Composable
fun TrackItem(track: Track, onClick: () -> Unit) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = track.image.getImage(context)),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(text = track.title, fontSize = 20.sp, color = Color.White)
            Text(text = track.artist, fontSize = 18.sp, color = Color.LightGray)
        }
    }
}