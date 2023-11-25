package uz.gita.musicplayerapp.presentor.screen.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.musicplayerapp.R
import uz.gita.musicplayerapp.data.models.AudioModel
import uz.gita.musicplayerapp.ui.theme.MusicPlayerAppTheme

class PlayScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        MusicPlayerAppTheme {
            val vm = getViewModel<PlayViewModel>()
            PlayScreenContent()
        }
    }
}

@Composable
fun PlayScreenContent(
//    musicData:AudioModel
) {

    Box(modifier = Modifier.fillMaxSize()) {
        var sliderPosition by remember { mutableStateOf(0f) }


        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 150.dp)
                .size(250.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                Image(
                    painter = painterResource(id = R.drawable.baseline_library_music_24),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
        }


        Text(
            text = sliderPosition.toString(), modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 140.dp)
                .align(Alignment.BottomCenter)
        )
        Slider(
            value = sliderPosition,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 150.dp)
                .align(Alignment.BottomCenter),
            onValueChange = { sliderPosition = it },
            valueRange = 0f..1f,
            onValueChangeFinished = {
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 100.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nextbutton1),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { }
                    .weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.pause),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { }
                    .weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.nextbutton),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { }
                    .weight(1f)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PlayScreenPreview() {
    PlayScreenContent()
}