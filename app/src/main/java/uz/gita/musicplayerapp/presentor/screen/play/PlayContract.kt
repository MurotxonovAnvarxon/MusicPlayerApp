package uz.gita.musicplayerapp.presentor.screen.play

import kotlinx.coroutines.flow.StateFlow
import uz.gita.musicplayerapp.data.models.AudioModel

interface PlayContract {

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)

    }


    data class UIState(
        val data: List<AudioModel> = listOf(),
    )

    interface Intent {
        object Back : Intent
    }

}