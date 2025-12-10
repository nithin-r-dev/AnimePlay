package org.anime.assessment.utils

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConnectivityIndicatorHandler(private val isVisible: MutableState<Boolean>, private val coroutineScope: CoroutineScope) {
    fun showIndicator() {
        isVisible.value = true
        coroutineScope.launch {
            delay(1000)
            isVisible.value = false
        }
    }

    val visibleCheck: Boolean get() = isVisible.value
}