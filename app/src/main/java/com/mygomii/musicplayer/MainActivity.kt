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
                        LazyColumn(
                            modifier = Modifier
                                .background(Color.Black),
                            contentPadding = PaddingValues(5.dp)
                        ) {

                            items(getTracks.size) { index ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 4.dp)
                                        .clip(shape = RoundedCornerShape(8.dp))
                                        .background(color = Color.DarkGray)
                                        .padding(all = 12.dp)
                                        .clickable {
                                            println("#### ${getTracks[index]}")

                                            if (currentTrack == null || currentTrack != getTracks[index]) {
                                                currentTrack = getTracks[index]
                                                showPlayingView = true
                                                playTrack(getTracks[index].uri)
                                                isPlaying = true

                                                return@clickable
                                            }


                                            if (currentTrack == getTracks[index]) {
                                                showPlayingView = true
                                            }
                                        }

                                ) {
                                    Image(
                                        painter = painterResource(id = getTracks[index].image.getImage(LocalContext.current)),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(60.dp)
                                    )

                                    Column {
                                        Text(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
                                            text = getTracks[index].title,
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                        Text(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                                            text = getTracks[index].artist,
                                            fontSize = 18.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (currentTrack != null && showPlayingView) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            IconButton(
                                onClick = { showPlayingView = false },
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
                                painter = painterResource(id = currentTrack!!.image.getImage(LocalContext.current)),
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
                                text = currentTrack!!.title,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            )

                            Text(
                                color = Color.LightGray,
                                text = currentTrack!!.artist,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            IconButton(onClick = {
                                if (exoPlayer.isPlaying) {
                                    exoPlayer.pause()
                                    isPlaying = false
                                } else {
                                    exoPlayer.play()
                                    isPlaying = true
                                }
                            }, modifier = Modifier.size(48.dp)) {
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
                                    exoPlayer.seekTo(newPosition.toLong())
                                    playbackPosition = newPosition.toLong()

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
                                Text(text = playbackPosition.toString(), color = Color.White)
                                Text(text = duration.toString(), color = Color.White)
                            }
                        }

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
        exoPlayer.release()

        super.onDestroy()
    }
}

