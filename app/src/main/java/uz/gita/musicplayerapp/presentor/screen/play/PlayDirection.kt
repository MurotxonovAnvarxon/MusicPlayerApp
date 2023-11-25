package uz.gita.musicplayerapp.presentor.screen.play

import uz.gita.musicplayerapp.navigator.AppNavigator
import javax.inject.Inject

interface PlayDirection {
    suspend fun back()
}


class PlayDirectionImpl @Inject constructor(
    private val direction: AppNavigator
) : PlayDirection {
    override suspend fun back() {
        direction.back()
    }

}