package com.mygomii.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.media3.exoplayer.ExoPlayer
import com.mygomii.musicplayer.data.models.toMediaItemList
import com.mygomii.musicplayer.presentations.MainScreen
import com.mygomii.musicplayer.presentations.detail.DetailScreen
import com.mygomii.musicplayer.presentations.getTracks
import com.mygomii.musicplayer.ui.theme.MusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tracks = getTracks

        Player.initializePlayer(this, tracks.toMediaItemList())

        setContent {
            MusicPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        DetailScreen(tracks[0])
                    }
                }
            }
        }
    }
}
