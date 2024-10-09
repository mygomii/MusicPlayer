package com.mygomii.musicplayer.data.models

import android.content.Context
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


