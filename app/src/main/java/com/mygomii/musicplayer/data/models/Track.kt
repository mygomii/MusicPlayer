package com.mygomii.musicplayer.data.models

import android.content.Context
import androidx.media3.common.MediaItem
import kotlinx.serialization.Serializable


@Serializable
data class Track(
    val id: Int,
    val title: String,
    val uri: String,
    val image: String,
    val artist: String,
    var isSelected: Boolean = false,
)

fun String.getImage(context: Context): Int {
    return context.resources.getIdentifier(this, "drawable", context.packageName)
}

fun List<Track>.toMediaItemList(): MutableList<MediaItem> {
    return this.map { MediaItem.fromUri(it.uri) }.toMutableList()
}

fun MutableList<Track>.resetTracks() {
    this.forEach { track ->
        track.isSelected = false
//        track.state = PlayerStates.STATE_IDLE
    }
}