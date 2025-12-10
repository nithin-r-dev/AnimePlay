package org.anime.assessment.ui.detailsScreen.presentation.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import org.anime.assessment.R
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.database.classes.AnimeProductionDetails
import org.anime.assessment.ui.commonView.CommonComposable.Companion.getDynamicFontSize
import org.anime.assessment.ui.detailsScreen.presentation.viewmodel.AnimeDetailsViewModel
import org.anime.assessment.ui.homeScreen.presentation.screen.ScoreView
import org.anime.assessment.ui.youtubePlayer.presentation.screen.FloatingYouTubePlayer
import org.anime.assessment.utils.DateUtils
import org.anime.assessment.utils.Utility
import com.intuit.sdp.R as sdp

@Composable
fun AnimeDetailsScreen(activity: Activity) {
    val viewModel = viewModel<AnimeDetailsViewModel>()
    val details by viewModel.animeDetails.collectAsState()
    val productionDetails by viewModel.productionDetails.collectAsState()
    val studioDetails by viewModel.studioDetails.collectAsState()
    val genreDetails by viewModel.genreDetails.collectAsState()
    val showTrailer by viewModel.showTrailer.collectAsState()
    val alert by viewModel.alert.collectAsState()
    val context = LocalContext.current
    DisposableEffect(Unit) {
        onDispose {
            viewModel.toggleTrailerView(false)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getProdDetails(details?.id ?: 0)
    }
    Scaffold(
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
        // Collapsing header (image + details)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            CollapsingHeader(details, showTrailer, viewModel)
            Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._10sdp)))
            DetailsOverTextView(stringResource(R.string.overview), details?.synopsis ?: "")
            DetailsTextView(stringResource(R.string.episodes), (details?.episodes ?: 0).toString())
            DetailsTextView(stringResource(R.string.season), details?.season ?: "")
            DetailsTextView(
                stringResource(R.string.aired),
                DateUtils.callDateFormatChangeMethod(
                    details?.lastAired ?: "",
                    DateUtils.NORMAL_FORMAT,
                    DateUtils.NORMAL_FORMAT_FOR_UI
                )
            )
            DetailsTextView(stringResource(R.string.duration), details?.duration ?: "")
            DetailsTextView(stringResource(R.string.status), details?.airingStatus ?: "")
            if (genreDetails.isNotEmpty()) {
                GenerDetails("Genres", genreDetails)
            }
            if (studioDetails.isNotEmpty()) {
                DetailsTextView(
                    "Studio(s)",
                    studioDetails.joinToString(", ") { it.name ?: "" })
            }
            if (productionDetails.isNotEmpty()) {
                DetailsTextView(
                    "Producer(s)",
                    productionDetails.joinToString(", ") { it.name ?: "" })
            }

            Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._25sdp)))
        }
        FloatingYouTubePlayer(activity, showTrailer, details?.videoUrl ?: "") {
            viewModel.toggleTrailerView(false)
        }
        if (alert.isNotEmpty()) {
            Utility.showToast(alert, context)
            viewModel.updateAlert("")
        }
    }


}

@Composable
fun GenerDetails(title: String, generDetails: List<AnimeProductionDetails>) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(sdp.dimen._15sdp),
                vertical = dimensionResource(sdp.dimen._5sdp)
            )

    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = getDynamicFontSize(
                defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value,
                minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                maxValue = dimensionResource(id = sdp.dimen._15sdp).value
            ),

            style = TextStyle(color = colorResource(R.color.white)),
            fontFamily = FontFamily(Font(R.font.poppins_bold))
        )
        Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._15sdp)))
        generDetails.forEach { gener ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = gener.name ?: "",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(sdp.dimen._5sdp)))
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.white),
                            shape = RoundedCornerShape(dimensionResource(sdp.dimen._5sdp))
                        )
                        .padding(
                            vertical = dimensionResource(sdp.dimen._2sdp),
                            horizontal = dimensionResource(sdp.dimen._5sdp)
                        ),
                    fontSize = getDynamicFontSize(
                        defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value,
                        minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                        maxValue = dimensionResource(id = sdp.dimen._15sdp).value
                    ),

                    style = TextStyle(color = colorResource(R.color.white)),
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )
                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
            }

        }
    }
}

@Composable
fun DetailsOverTextView(title: String, value: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(sdp.dimen._15sdp),
                vertical = dimensionResource(sdp.dimen._7sdp)
            )
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = getDynamicFontSize(
                defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value,
                minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                maxValue = dimensionResource(id = sdp.dimen._15sdp).value
            ),

            style = TextStyle(color = colorResource(R.color.white)),
            fontFamily = FontFamily(Font(R.font.poppins_bold))
        )
        Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._15sdp)))
        Text(
            text = value,
            textAlign = TextAlign.Start,
            fontSize = getDynamicFontSize(
                defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value,
                minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                maxValue = dimensionResource(id = sdp.dimen._15sdp).value
            ),

            style = TextStyle(color = colorResource(R.color.white)),
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}

@Composable
fun DetailsTextView(title: String, value: String) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(sdp.dimen._15sdp),
                vertical = dimensionResource(sdp.dimen._5sdp)
            )

    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = getDynamicFontSize(
                defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value,
                minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                maxValue = dimensionResource(id = sdp.dimen._15sdp).value
            ),

            style = TextStyle(color = colorResource(R.color.white)),
            fontFamily = FontFamily(Font(R.font.poppins_bold))
        )
        Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._15sdp)))
        Text(
            text = value,
            textAlign = TextAlign.Start,
            fontSize = getDynamicFontSize(
                defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value,
                minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                maxValue = dimensionResource(id = sdp.dimen._15sdp).value
            ),

            style = TextStyle(color = colorResource(R.color.white)),
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CollapsingHeader(
    details: AnimeHomeClass?,
    isTrailerVisible: Boolean,
    viewModel: AnimeDetailsViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val headerHeight = screenHeight * 0.40f   // exactly 30% of the device screen

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)        // <-- Now it stops at 30%
            .background(colorResource(R.color.grey_100)),
        contentAlignment = Alignment.Center
    ) {

        // Blurred background image
        GlideImage(
            model = details?.imageUrl ?: "",
            contentDescription = null,
            loading = placeholder(R.drawable.default_image_place_holder),
            failure = placeholder(R.drawable.default_image_place_holder),
            contentScale = ContentScale.Crop,
            requestBuilderTransform = {
                it.apply(bitmapTransform(BlurTransformation(25, 3)))
            },
            modifier = Modifier.fillMaxSize()
        )

        // Foreground content
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GlideImage(
                model = details?.imageUrl ?: "",
                contentDescription = null,
                loading = placeholder(R.drawable.default_image_place_holder),
                failure = placeholder(R.drawable.default_image_place_holder),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(dimensionResource(id = sdp.dimen._105sdp))
                    .height(dimensionResource(id = sdp.dimen._120sdp))
                    .clip(RoundedCornerShape(dimensionResource(sdp.dimen._5sdp)))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = details?.name ?: "",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                ),
            )

            Spacer(modifier = Modifier.height(dimensionResource(sdp.dimen._10sdp)))

            ScoreView(details?.score ?: "")
            Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clip(RoundedCornerShape(dimensionResource(sdp.dimen._5sdp)))
            ) {
                val ratingSplit = (details?.rating ?: "").split(" - ")
                HeaderDetailsView("#${details?.rank ?: 0}", R.color.grey_100)
                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._3sdp)))
                HeaderDetailsView(
                    if (ratingSplit.isNotEmpty()) ratingSplit[0] else "",
                    R.color.purple_200
                )
                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._3sdp)))
                HeaderDetailsView(details?.type ?: "", R.color.light_blue)
            }
            Spacer(modifier = Modifier.height(dimensionResource(sdp.dimen._5sdp)))

            if (!details?.videoUrl.isNullOrEmpty()) {
                WatchTrailerButton(onClick = {
                    if (!isTrailerVisible) viewModel.toggleTrailerView(true)
                })
            }
        }
    }
}


@Composable
fun WatchTrailerButton(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(sdp.dimen._10sdp)))
            .background(
                color = colorResource(R.color.green_100),
                shape = RoundedCornerShape(dimensionResource(sdp.dimen._10sdp))
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = dimensionResource(sdp.dimen._10sdp),
                vertical = dimensionResource(sdp.dimen._15sdp)
            )

    ) {
        Text(
            text = stringResource(R.string.watch_trailer),
            textAlign = TextAlign.Start,
            fontSize = getDynamicFontSize(
                defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._13ssp).value,
                minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                maxValue = dimensionResource(id = sdp.dimen._15sdp).value
            ),
            style = TextStyle(color = colorResource(R.color.white)),
            fontFamily = FontFamily(Font(R.font.poppins_bold))
        )
        Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
        Image(
            painter = painterResource(R.drawable.video_icon),
            contentDescription = "Score icon",
            modifier = Modifier.size(
                dimensionResource(sdp.dimen._10sdp)
            )
        )
    }
}

@Composable
fun HeaderDetailsView(details: String, color: Int) {
    Text(
        text = details,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(
                color = colorResource(color),
            )
            .padding(
                vertical = dimensionResource(sdp.dimen._3sdp),
                horizontal = dimensionResource(sdp.dimen._7sdp)
            ),
        fontSize = getDynamicFontSize(
            defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._17ssp).value,
            minValue = dimensionResource(id = sdp.dimen._9sdp).value,
            maxValue = dimensionResource(id = sdp.dimen._19sdp).value
        ),
        maxLines = 1,
        style = TextStyle(color = colorResource(R.color.white)),
        fontFamily = FontFamily(Font(R.font.poppins_medium))
    )
}
