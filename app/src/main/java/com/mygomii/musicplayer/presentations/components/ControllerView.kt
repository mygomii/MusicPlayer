package com.mygomii.musicplayer.presentations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mygomii.musicplayer.R
import com.mygomii.musicplayer.data.models.Track

@Composable
fun PreviousIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.prev),
            contentDescription = "",
            modifier = Modifier.size(48.dp),
            tint = Color.White

        )
    }
}

@Composable
fun PlayPauseIcon(selectedTrack: Track, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp, color = Color(0x33000000),
                shape = RoundedCornerShape(size = 26.dp)
            )
            .width(80.dp)
            .height(80.dp)
            .background(
                color = Color(0xFF222222),
                shape = RoundedCornerShape(size = 26.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(
                    // if (selectedTrack.state == PlayerStates.STATE_PLAYING) {
                    // R.drawable.pause
                    // } else {
                    R.drawable.play
                    //}
                ),
                contentDescription = "",
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )
        }
    }

}


@Composable
fun NextIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.next),
            contentDescription = "",
            modifier = Modifier.size(48.dp),
            tint = Color.White
        )
    }
}
