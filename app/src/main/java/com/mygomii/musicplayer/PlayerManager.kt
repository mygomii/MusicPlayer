package com.mygomii.musicplayer

import android.content.Context
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerManager(context: Context) {
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    private val playerListener = object : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            println("##### $error")
            notifyError(error)
        }
    }

    init {
        exoPlayer.addListener(playerListener)
    }

    private fun notifyError(error: PlaybackException) {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(
                MusicPlayerApp.app,
                "재생 중 오류가 발생했습니다: ${error.errorCodeName}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val isPlaying: Boolean
        get() = exoPlayer.isPlaying

    val currentPosition: Long
        get() = exoPlayer.currentPosition

    val duration: Long
        get() = exoPlayer.duration

    fun playTrack(uri: String) {
        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            play()
        }
    }

    fun play() {
        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    fun release() {
        exoPlayer.release()
    }
}