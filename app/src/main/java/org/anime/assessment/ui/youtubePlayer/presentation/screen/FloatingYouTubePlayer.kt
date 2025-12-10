package org.anime.assessment.ui.youtubePlayer.presentation.screen


import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.anime.assessment.R
import org.anime.assessment.ui.youtubePlayer.presentation.viewmodel.FloatingPlayerViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FloatingYouTubePlayer(
    activity: Activity,
    isVisible: Boolean,
    videoUrl: String,
    onClose: () -> Unit
) {
    val viewModel = viewModel<FloatingPlayerViewModel>()
    val offsetX by viewModel.offsetX.collectAsState()
    val offsetY by viewModel.offsetY.collectAsState()
    val playbackPos by viewModel.playbackPos.collectAsState()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    val density = LocalDensity.current

    val screenWidthPx = with(density) { screenWidthDp.toPx() }
    val screenHeightPx = with(density) { screenHeightDp.toPx() }

    // 80% width, 16:9 height
    val targetWidth = screenWidthDp * 0.8f
    val targetHeight = targetWidth * 9 / 16f

    val targetWidthPx = with(density) { targetWidth.toPx() }
    val targetHeightPx = with(density) { targetHeight.toPx() }

    LaunchedEffect(isVisible, configuration.orientation) {
        if (isVisible) {
            val centerX = (screenWidthPx - targetWidthPx) / 2f
            val centerY = (screenHeightPx - targetHeightPx) / 2f
            viewModel.updateXOffset(centerX)
            viewModel.updateYOffset(centerY)
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            // Floating Player
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val maxX = (screenWidthPx - targetWidthPx).coerceAtLeast(0f)
                            val maxY = (screenHeightPx - targetHeightPx).coerceAtLeast(0f)
                            val newX = (offsetX + dragAmount.x).coerceIn(0f, maxX)
                            val newY = (offsetY + dragAmount.y).coerceIn(0f, maxY)
                            viewModel.updateXOffset(newX)
                            viewModel.updateYOffset(newY)
                        }
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black)
                    .size(width = targetWidth, height = targetHeight)
            ) {
                YouTubePlayerScreen(
                    activity,
                    viewModel.extractYoutubeId(videoUrl),
                    playbackPos,
                    viewModel
                )
            }
            // --- Close Button ---
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (offsetX + targetWidthPx - 15.dp.toPx()).toInt(),
                            y = (offsetY - 10.dp.toPx()).toInt()
                        )
                    }
                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    .size(32.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.close_icon),
                    contentDescription = "Close Player",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}


@Composable
fun YouTubePlayerScreen(
    activity: Activity,
    videoID: String,
    playbackPos: Float,
    viewModel: FloatingPlayerViewModel
) {
    val window = activity.window
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    SideEffect {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                show(WindowInsetsCompat.Type.statusBars())
                show(WindowInsetsCompat.Type.navigationBars())
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16 / 9f),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            coroutineScope.launch {
                                delay(300)
                                youTubePlayer.loadVideo(videoID, playbackPos)
                            }
                        }

                        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                            viewModel.updatePlaybackPos(second)
                        }
                    })
                }
            }
        )
    }
}


