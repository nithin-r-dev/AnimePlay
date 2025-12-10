package org.anime.assessment.ui.commonView

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.anime.assessment.R
import com.intuit.sdp.R as sdp
import com.intuit.ssp.R as ssp

class CommonComposable {
    companion object {

        private val TextUnit.nonScaledSp
            @Composable
            get() = (this.value / LocalDensity.current.fontScale).sp


        @Composable
        fun getDynamicFontSize(defaultValue: Float, minValue: Float, maxValue: Float): TextUnit {
            val configuration = LocalConfiguration.current
            val systemFontScale = configuration.fontScale
            val finalSize = (defaultValue * systemFontScale).coerceIn(minValue, maxValue)
            return finalSize.sp.nonScaledSp
        }

        @Composable
        fun Modifier.shimmerLoading(
            durationMillis: Int = 1000,
        ): Modifier {
            val transition = rememberInfiniteTransition(label = "")

            val translateAnimation by transition.animateFloat(
                initialValue = 0f,
                targetValue = 500f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                ),
                label = "",
            )

            return background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.LightGray.copy(alpha = 0.2f),
                        Color.LightGray.copy(alpha = 1.0f),
                        Color.LightGray.copy(alpha = 0.2f),
                    ),
                    start = Offset(x = translateAnimation, y = translateAnimation),
                    end = Offset(x = translateAnimation + 100f, y = translateAnimation + 100f),
                )
            )
        }

        @Composable
        fun NoRecordFoundWithPadding(text: String = stringResource(R.string.no_record_found)) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(sdp.dimen._100sdp)),
                fontSize = getDynamicFontSize(
                    defaultValue = dimensionResource(id = ssp.dimen._15ssp).value,
                    minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                    maxValue = dimensionResource(id = sdp.dimen._17sdp).value
                ),

                style = TextStyle(color = colorResource(R.color.black)),
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
        }

        @Composable
        fun HeaderView(text: String, isBackNeeded: Boolean, onBackClick: () -> Unit) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(com.intuit.sdp.R.dimen._8sdp)),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.black)),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(R.color.black)
                        )
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(bottom = dimensionResource(sdp.dimen._17sdp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (isBackNeeded) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.header_back_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(
                                        dimensionResource(sdp.dimen._20sdp)
                                    )
                                    .clickable {
                                        onBackClick()
                                    }
                            )
                        }
                    }
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontSize = getDynamicFontSize(
                            defaultValue = dimensionResource(id = ssp.dimen._15ssp).value,
                            minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                            maxValue = dimensionResource(id = sdp.dimen._17sdp).value
                        ),

                        style = TextStyle(color = colorResource(R.color.white)),
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                    )
                }
            }

        }
    }
}