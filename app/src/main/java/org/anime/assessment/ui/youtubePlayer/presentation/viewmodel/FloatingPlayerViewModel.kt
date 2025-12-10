package org.anime.assessment.ui.youtubePlayer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.anime.assessment.utils.Utility

class FloatingPlayerViewModel : ViewModel() {


    private val _offsetX = MutableStateFlow(0f)
    val offsetX: StateFlow<Float> get() = _offsetX

    private val _offsetY = MutableStateFlow(0f)
    val offsetY: StateFlow<Float> get() = _offsetY

    private val _playbackPos = MutableStateFlow(0f)
    val playbackPos: StateFlow<Float> get() = _playbackPos

    /**
     * Method to update x offset of drag
     */
    fun updateXOffset(value: Float) {
        _offsetX.value = value
    }

    /**
     * Method to update x offset of drag
     */
    fun updateYOffset(value: Float) {
        _offsetY.value = value
    }

    /**
     * Method to extract youtube id from video
     */
    fun extractYoutubeId(url: String): String {
        val regex = "(?<=v=)[^&#]+|(?<=be/)[^&#]+|(?<=embed/)[^&#]+|(?<=shorts/)[^&#]+".toRegex()
        val match = regex.find(url)?.value

        val videoId = match
            ?.substringBefore('?')   // remove ?si= or any other params
            ?.substringBefore('&')   // remove &t= or other query params
            ?.trim()

        Utility.printLogConsole("##extractYoutubeId", "---------->url returned->${videoId}")

        return videoId ?: url
    }


    fun updatePlaybackPos(value: Float) {
        _playbackPos.value = value

    }
}