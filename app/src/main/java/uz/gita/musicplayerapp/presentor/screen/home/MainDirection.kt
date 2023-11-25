package uz.gita.musicplayerapp.presentor.screen.home

import uz.gita.musicplayerapp.navigator.AppNavigator
import uz.gita.musicplayerapp.presentor.screen.play.PlayScreen
import javax.inject.Inject

interface MainDirection {
    suspend fun MoveToPlay()
}

class MainDirectionImpl @Inject constructor(
    val direction: AppNavigator,
) : MainDirection {

    override suspend fun MoveToPlay() {
        direction.openWithSave(PlayScreen())
    }

}