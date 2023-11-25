package uz.gita.musicplayerapp.presentor.screen.home

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.formatDuration
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import uz.gita.musicplayerapp.domain.repository.AudioRepository
import uz.gita.musicplayerapp.player.service.AudioService
import uz.gita.musicplayerapp.player.service.AudioServiceHandler
import uz.gita.musicplayerapp.player.service.AudioState
import uz.gita.musicplayerapp.player.service.PlayerEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val audioServiceHandler: AudioServiceHandler,
    private val repository: AudioRepository,
    private val direction: MainDirection,
    @ApplicationContext
    val context: Context,
) : MainContract.ViewModel.ScreenModel, ScreenModel, MainContract.ViewModel {
    private var isServiceRunning = false

    override val container =
        coroutineScope.container<MainContract.UIState, MainContract.SideEffect>(MainContract.UIState())

    init {
        audioServiceHandler.audioState.onEach { mediaState ->
            when (mediaState) {
                AudioState.Initial -> {}
                is AudioState.Buffering -> calculateProgressValue(mediaState.progress)
                is AudioState.Playing -> intent { reduce { this.state.copy(isAudioPlaying = mediaState.isPlaying) } }
                is AudioState.Progress -> calculateProgressValue(mediaState.progress)
                is AudioState.CurrentPlaying -> intent { reduce { this.state.copy(currentAudioModel = container.stateFlow.value.audioList[mediaState.mediaItemIndex]) } }
                is AudioState.Ready -> intent { reduce { this.state.copy(duration = mediaState.duration) } }
            }
        }.launchIn(coroutineScope)
    }

    override fun onEventDispatcher(event: MainContract.Event) = intent {
        when (event) {
            MainContract.Event.LoadAudioData -> loadAudioData()
            MainContract.Event.onPrev -> audioServiceHandler.onPlayerEvents(PlayerEvent.SeekToPrev)
            MainContract.Event.onNext -> audioServiceHandler.onPlayerEvents(PlayerEvent.SeekToNext)
            MainContract.Event.onStart -> audioServiceHandler.onPlayerEvents(PlayerEvent.PlayPause)
            is MainContract.Event.OnItemClick -> selectMusic(event.index)
            MainContract.Event.MoveToPlay -> {
                direction.MoveToPlay()
            }
        }
    }


    private fun selectMusic(index: Int) {
        audioServiceHandler.onPlayerEvents(
            PlayerEvent.SelectedAudioChange,
            selectedAudioIndex = index
        )
        startService()
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(context, AudioService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            isServiceRunning = true
        }
    }

    override fun onDispose() {
        coroutineScope.launch {
            audioServiceHandler.onPlayerEvents(PlayerEvent.Stop)
        }
        super.onDispose()
    }

    private fun loadAudioData() = intent {
        val audio = repository.getAudioData()
        reduce { this.state.copy(audioList = audio) }
        setMediaItems()
    }

    private fun setMediaItems() {
        container.stateFlow.value.audioList.map { audio ->
            MediaItem.Builder()
                .setUri(audio.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.artist)
                        .setDisplayTitle(audio.title)
                        .setSubtitle(audio.title)
                        .build()
                ).build()
        }.also { audioServiceHandler.setMediaItemList(it) }

    }

    private fun calculateProgressValue(currentProgress: Long) = intent {
        reduce {
            this.state.copy(
                progressString = formatDuration(currentProgress),
                progress = if (currentProgress > 0) ((currentProgress.toFloat() / container.stateFlow.value.duration.toFloat()) * 100f) else 0f
            )
        }
    }
}