package com.mygomii.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.mygomii.musicplayer.data.models.Track
import com.mygomii.musicplayer.data.models.getImage
import com.mygomii.musicplayer.data.models.getTracks
import com.mygomii.musicplayer.ui.theme.MusicPlayerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


class MainActivity : ComponentActivity() {
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exoPlayer = ExoPlayer.Builder(this).build()

        setContent {
            MusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentTrack by remember { mutableStateOf<Track?>(null) }
                    var showPlayingView by remember { mutableStateOf(false) }
                    var isPlaying by remember { mutableStateOf(false) }
                    var playbackPosition by remember { mutableLongStateOf(0L) }
                    var duration by remember { mutableLongStateOf(0L) }

                    LaunchedEffect(exoPlayer) {
                        while (isActive) {
                            if (exoPlayer.isPlaying) {
                                playbackPosition = exoPlayer.currentPosition
                                duration = exoPlayer.duration
                            }
                            delay(500L)
                        }
                    }

                    Column {
                        TrackList(getTracks) { selectedTrack ->
                            println("#### $selectedTrack")

                            if (currentTrack == null || currentTrack != selectedTrack) {
                                currentTrack = selectedTrack
                                showPlayingView = true
                                playTrack(selectedTrack.uri)
                                isPlaying = true

                                return@TrackList
                            }


                            if (currentTrack == selectedTrack) {
                                showPlayingView = true
                            }
                        }
                    }

                    if (currentTrack != null && showPlayingView) {
                        PlayerControls(
                            track = currentTrack!!,
                            isPlaying = isPlaying,
                            playbackPosition = playbackPosition,
                            duration = duration,
                            onPlayPause = {
                                if (exoPlayer.isPlaying) {
                                    exoPlayer.pause()
                                    isPlaying = false
                                } else {
                                    exoPlayer.play()
                                    isPlaying = true
                                }
                            },
                            onSeek = { position ->
                                exoPlayer.seekTo(position)
                                playbackPosition = position
                            },
                            onCloseClick = {
                                showPlayingView = false
                            }
                        )
                    }


                }
            }
        }
    }

    private fun playTrack(uri: String) {
        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            play()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}


@Composable
fun TrackList(tracks: List<Track>, onTrackSelected: (Track) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .background(Color.Black),
        contentPadding = PaddingValues(5.dp)
    ) {

        items(tracks.size) { index ->
            TrackListItem(track = tracks[index], onTrackSelected)
        }
    }

}

@Composable
fun TrackListItem(track: Track, onTrackSelected: (Track) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.DarkGray)
            .padding(all = 12.dp)
            .clickable { onTrackSelected(track) }

    ) {
        Image(
            painter = painterResource(id = track.image.getImage(LocalContext.current)),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
        )

        Column {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
                text = track.title,
                fontSize = 20.sp,
                color = Color.White
            )
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                text = track.artist,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}


@Composable
fun PlayerControls(
    track: Track,
    isPlaying: Boolean,
    playbackPosition: Long,
    duration: Long,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onCloseClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = onCloseClick,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = Color.White,
            )
        }
        Image(
            painter = painterResource(id = track.image.getImage(LocalContext.current)),
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

        Text(
            color = Color.White,
            text = track.title,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )

        Text(
            color = Color.LightGray,
            text = track.artist,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(onClick = onPlayPause, modifier = Modifier.size(48.dp)) {
            Icon(
                painter = painterResource(if (isPlaying) R.drawable.pause else R.drawable.play),
                modifier = Modifier.size(48.dp),
                tint = Color.White,
                contentDescription = if (isPlaying) "Pause" else "Play"
            )
        }

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatTime(playbackPosition), color = Color.White)
            Text(text = formatTime(duration), color = Color.White)
        }

    }
}

fun formatTime(millis: Long): String {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}

