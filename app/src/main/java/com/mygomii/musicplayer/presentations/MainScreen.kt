package com.mygomii.musicplayer.presentations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mygomii.musicplayer.data.models.Track
import com.mygomii.musicplayer.presentations.detail.DetailScreen
import com.mygomii.musicplayer.presentations.home.HomeScreen

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    tracks: List<Track>,
) {
    NavHost(navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(tracks, onNavigateToTrack = { track ->
                navController.navigate(track)
            })
        }
        composable<Track> { backStackEntry ->
            val track: Track = backStackEntry.toRoute()
//            Player.setUpTrack(track.id, true)
            DetailScreen(track)
        }
    }

}
