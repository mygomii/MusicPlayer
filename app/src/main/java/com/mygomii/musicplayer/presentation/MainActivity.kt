package com.mygomii.musicplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mygomii.musicplayer.PlayerManager
import com.mygomii.musicplayer.presentation.screens.MusicPlayerScreen
import com.mygomii.musicplayer.ui.theme.MusicPlayerTheme


class MainActivity : ComponentActivity() {
    private lateinit var playerManager: PlayerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        playerManager = PlayerManager(this)


        setContent {
            MusicPlayerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MusicPlayerScreen(playerManager)
                }
            }
        }

    }

    override fun onDestroy() {
        playerManager.release()
        super.onDestroy()
    }
}

