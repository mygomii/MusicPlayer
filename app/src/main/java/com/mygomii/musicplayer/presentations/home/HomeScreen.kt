package com.mygomii.musicplayer.presentations.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mygomii.musicplayer.data.models.Track
import com.mygomii.musicplayer.presentations.components.TrackListItem

@Composable
fun HomeScreen(tracks: List<Track>, onNavigateToTrack: (Track) -> Unit) {
    TrackList(
        tracks = tracks,
    ) { track ->
        onNavigateToTrack(track)
    }
}


@Composable
fun TrackList(
    tracks: List<Track>,
    onItemClick: (Track) -> Unit,
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxSize()
                .background(Color.Black),
            contentPadding = PaddingValues(5.dp)
        ) {

            items(tracks.size) { index ->
                TrackListItem(
                    track = tracks[index],
                    onTrackClick = {
                        onItemClick(tracks[index])
//                            playerEvents.onTrackCl.ick(tracks[index])
//                            navController.navigate("detail/${index}")
                    })
            }
        }

    }
}
