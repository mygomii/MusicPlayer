package com.mygomii.musicplayer

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class PlayerManager(context: Context) {
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

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