package uz.gita.musicplayerapp.presentor.screen.play

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uz.gita.musicplayerapp.domain.repository.AudioRepository
import uz.gita.musicplayerapp.player.service.AudioServiceHandler
import uz.gita.musicplayerapp.presentor.screen.home.MainContract
import javax.inject.Inject

class PlayViewModel @Inject constructor(


    private val audioServiceHandler: AudioServiceHandler,
    private val repository: AudioRepository,
    private val direction:PlayDirection
) : ViewModel(), PlayContract.ViewModel {
    override val uiState = MutableStateFlow(PlayContract.UIState())

    override fun onEventDispatcher(intent: PlayContract.Intent) {

    }
}