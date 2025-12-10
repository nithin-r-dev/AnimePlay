package org.anime.assessment.ui.homeScreen.presentation.screen

import android.widget.Toast
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import org.anime.assessment.R
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.ui.commonView.CommonComposable.Companion.HeaderView
import org.anime.assessment.ui.commonView.CommonComposable.Companion.NoRecordFoundWithPadding
import org.anime.assessment.ui.commonView.CommonComposable.Companion.getDynamicFontSize
import org.anime.assessment.ui.commonView.CommonComposable.Companion.shimmerLoading
import org.anime.assessment.ui.homeScreen.presentation.viewmodel.HomeScreenViewModel
import com.intuit.sdp.R as sdp


@Composable
fun AnimeHomeScreen(onDetailsNav: () -> Unit) {
    val viewModel = viewModel<HomeScreenViewModel>()
    val homeList by viewModel.homeList.collectAsState()
    val alertMessage by viewModel.alertMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.layoutInfo }.collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                val isAtBottom = lastVisibleItem >= totalItems - 2
                if (isAtBottom && !isLoading && viewModel.initiated) {
                    viewModel.loadMore(context)
                }
            }
    }
    LaunchedEffect(Unit) {
        viewModel.initializeView(context)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.black))

    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderView("Anime List", false, onBackClick = {})
            Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._10sdp)))
            if (!isLoading && homeList.isEmpty()) {
                NoRecordFoundWithPadding()
            } else {
                LazyColumn(
                    state = scrollState, modifier = Modifier.fillMaxSize()
                ) {
                    if (homeList.isNotEmpty()) {
                        items(homeList) { data ->
                            HomeListComponent(data) {
                                viewModel.updateSelectedAnimeToPref(data.id)
                                onDetailsNav()
                            }
                        }
                    } else {
                        items(5) {
                            HomeListShimmer()
                        }
                    }
                    if (isLoading) {
                        items(2) {
                            HomeListShimmer()
                        }
                    }
                }
            }
        }
    }
    if (alertMessage.isNotEmpty()) {
        Toast.makeText(context, alertMessage, Toast.LENGTH_SHORT).show()
        viewModel.updateAlertMessage("")
    }
}

@Composable
fun HomeListShimmer() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(sdp.dimen._10sdp))
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(sdp.dimen._15sdp),
                    horizontal = dimensionResource(sdp.dimen._10sdp)
                )
        ) {
            Box(
                modifier = Modifier
                    .width(dimensionResource(id = sdp.dimen._70sdp))
                    .height(dimensionResource(id = sdp.dimen._85sdp))
                    .clip(
                        RoundedCornerShape(
                            dimensionResource(sdp.dimen._5sdp)
                        )
                    )
                    .shimmerLoading()
            )
            Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._10sdp)))
            Column(
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .width(dimensionResource(id = sdp.dimen._60sdp))
                            .height(dimensionResource(id = sdp.dimen._20sdp))
                            .clip(
                                RoundedCornerShape(
                                    dimensionResource(sdp.dimen._5sdp)
                                )
                            )
                            .shimmerLoading()
                    )
                    Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
                    Box(
                        modifier = Modifier
                            .width(dimensionResource(id = sdp.dimen._60sdp))
                            .height(dimensionResource(id = sdp.dimen._20sdp))
                            .clip(
                                RoundedCornerShape(
                                    dimensionResource(sdp.dimen._5sdp)
                                )
                            )
                            .shimmerLoading()
                    )
                }

                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))

                Box(
                    modifier = Modifier
                        .width(dimensionResource(id = sdp.dimen._90sdp))
                        .height(dimensionResource(id = sdp.dimen._25sdp))
                        .clip(
                            RoundedCornerShape(
                                dimensionResource(sdp.dimen._5sdp)
                            )
                        )
                        .shimmerLoading()
                )

                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
                Box(
                    modifier = Modifier
                        .width(dimensionResource(id = sdp.dimen._60sdp))
                        .height(dimensionResource(id = sdp.dimen._20sdp))
                        .clip(
                            RoundedCornerShape(
                                dimensionResource(sdp.dimen._5sdp)
                            )
                        )
                        .shimmerLoading()
                )
                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
                Box(
                    modifier = Modifier
                        .width(dimensionResource(id = sdp.dimen._60sdp))
                        .height(dimensionResource(id = sdp.dimen._20sdp))
                        .clip(
                            RoundedCornerShape(
                                dimensionResource(sdp.dimen._5sdp)
                            )
                        )
                        .shimmerLoading()
                )

            }
        }
        Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.grey_200)
        )
    }
}

@Composable
fun HomeListComponent(homeClass: AnimeHomeClass, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(sdp.dimen._15sdp),
                    horizontal = dimensionResource(sdp.dimen._10sdp)
                )
        ) {
            AnimeImageView(homeClass.imageUrl, homeClass.episodes)
            Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._10sdp)))
            Column(
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "#${(homeClass.rank ?: 0)}",
                        textAlign = TextAlign.Center,
                        fontSize = getDynamicFontSize(
                            defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._13ssp).value,
                            minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                            maxValue = dimensionResource(id = sdp.dimen._13sdp).value
                        ),
                        style = TextStyle(color = colorResource(R.color.green_350)),
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                    )
                    Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
                    RatingsView(homeClass.rating)
                }
                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
                Text(
                    text = homeClass.name ?: "",
                    textAlign = TextAlign.Start,
                    fontSize = getDynamicFontSize(
                        defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._15ssp).value,
                        minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                        maxValue = dimensionResource(id = sdp.dimen._16sdp).value
                    ),

                    style = TextStyle(color = colorResource(R.color.white)),
                    fontFamily = FontFamily(Font(R.font.poppins_semin_bold))
                )

                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
                ScoreView(homeClass.score)
                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._15sdp)))
                Text(
                    text = homeClass.airingStatus ?: "",
                    textAlign = TextAlign.Center,
                    fontSize = getDynamicFontSize(
                        defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._13ssp).value,
                        minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                        maxValue = dimensionResource(id = sdp.dimen._13sdp).value
                    ),

                    style = TextStyle(color = colorResource(if (homeClass.isAiring == true) R.color.green_100 else R.color.red)),
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                )
                Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
                Text(
                    text = homeClass.duration ?: "",
                    textAlign = TextAlign.Center,
                    fontSize = getDynamicFontSize(
                        defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._13ssp).value,
                        minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                        maxValue = dimensionResource(id = sdp.dimen._13sdp).value
                    ),

                    style = TextStyle(color = colorResource(R.color.white)),
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                )
            }
        }
        Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth(.90f),
            color = colorResource(R.color.grey_100)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnimeImageView(imageUrl: String?, episodes: Int?) {
    Box(
        contentAlignment = Alignment.TopStart, modifier = Modifier.clip(
            RoundedCornerShape(
                dimensionResource(sdp.dimen._5sdp)
            )
        )
    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            loading = placeholder(R.drawable.default_image_place_holder),
            failure = placeholder(R.drawable.default_image_place_holder),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(dimensionResource(id = sdp.dimen._90sdp))
                .height(dimensionResource(id = sdp.dimen._105sdp))
                .clip(RoundedCornerShape(dimensionResource(sdp.dimen._5sdp)))
                .background(colorResource(R.color.white))

        )
        Text(
            text = "${episodes ?: 0} ${if ((episodes ?: 0) <= 1) "Episode" else "Episodes"}",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(max = dimensionResource(sdp.dimen._80sdp))
                .clip(RoundedCornerShape(bottomEnd = dimensionResource(sdp.dimen._10sdp)))
                .background(
                    color = colorResource(R.color.green_100),
                    shape = RoundedCornerShape(bottomEnd = dimensionResource(sdp.dimen._10sdp))
                )
                .padding(
                    vertical = dimensionResource(sdp.dimen._2sdp),
                    horizontal = dimensionResource(sdp.dimen._10sdp)
                ),
            fontSize = getDynamicFontSize(
                defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._10ssp).value,
                minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                maxValue = dimensionResource(id = sdp.dimen._11sdp).value
            ),

            style = TextStyle(color = colorResource(R.color.white)),
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}

@Composable
fun RatingsView(rating: String?) {
    Text(
        text = rating ?: "",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(sdp.dimen._5sdp)))
            .border(
                width = 1.dp,
                color = colorResource(R.color.purple_200),
                shape = RoundedCornerShape(dimensionResource(sdp.dimen._5sdp))
            )
            .padding(
                vertical = dimensionResource(sdp.dimen._3sdp),
                horizontal = dimensionResource(sdp.dimen._7sdp)
            ),
        fontSize = getDynamicFontSize(
            defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value,
            minValue = dimensionResource(id = sdp.dimen._9sdp).value,
            maxValue = dimensionResource(id = sdp.dimen._13sdp).value
        ),
        style = TextStyle(color = colorResource(R.color.purple_200)),
        fontFamily = FontFamily(Font(R.font.poppins_medium))
    )
}

@Composable
fun ScoreView(score: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(R.drawable.yellow_star_icon),
            contentDescription = "Score icon",
            modifier = Modifier.size(
                dimensionResource(sdp.dimen._10sdp)
            )
        )
        Spacer(modifier = Modifier.size(dimensionResource(sdp.dimen._5sdp)))
        Text(
            text = score ?: "",
            textAlign = TextAlign.Start,
            fontSize = getDynamicFontSize(
                defaultValue = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value,
                minValue = dimensionResource(id = sdp.dimen._9sdp).value,
                maxValue = dimensionResource(id = sdp.dimen._13sdp).value
            ),

            style = TextStyle(color = colorResource(R.color.white)),
            fontFamily = FontFamily(Font(R.font.poppins_medium))
        )
    }
}
