package org.anime.assessment.ui.navigation

sealed class Screens(val route: String) {
    data object AnimeHomeScreen : Screens("AnimeHomeScreen")
    data object AnimeDetailsScreen : Screens("AnimeDetailsScreen")

}