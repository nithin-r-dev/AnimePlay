package org.anime.assessment.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.anime.assessment.ui.detailsScreen.presentation.screen.AnimeDetailsScreen
import org.anime.assessment.ui.homeScreen.presentation.screen.AnimeHomeScreen
import org.anime.assessment.utils.Utility

@Composable
fun ScreenNavigation(activity: Activity, navController: NavHostController) {
    val initialScreen by remember { mutableStateOf(Screens.AnimeHomeScreen.route) }
    BackHandler {
        Utility.printLogConsole("##ONBACK", "-->AppNavigation")
        val handled = navController.handleBackNavigation()
        if (!handled) {
            activity.finish()
        }
    }
    NavHost(
        navController = navController, startDestination = initialScreen
    ) {
        composable(Screens.AnimeHomeScreen.route) {
            AnimeHomeScreen(onDetailsNav = {
                navController.navigateUI(Screens.AnimeDetailsScreen.route)
            })
        }
        composable(Screens.AnimeDetailsScreen.route) {
            AnimeDetailsScreen(activity)
        }
    }
}


/**
 * Handles the navigation in the app.
 * @param route - the name of the screen that need to navigate
 * @param isClearStack - boolean to indicate whether to remove back stacked screen or not.
 */
fun NavController.navigateUI(route: String, isClearStack: Boolean = true) {
    try {
        if (isClearStack) {
            this.navigateClearingBackStack(route)
        } else {
            this.navigate(route)
        }
    } catch (e: Exception) {
        Utility.printLogConsole("##NAVIGATE", "error-->${e.message}")
    }
}


/**
 * Method to clear the back stack and navigate to the target route.
 */
fun NavController.navigateClearingBackStack(targetRoute: String) {
    this.navigate(targetRoute) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}


private fun NavHostController.handleBackNavigation(): Boolean {
    val currentRoute = currentBackStackEntry?.destination?.route

    return when (currentRoute) {
        Screens.AnimeHomeScreen.route -> {
            this.popBackStack()
            false
        }

        Screens.AnimeDetailsScreen.route -> {
            this.navigateUI(Screens.AnimeHomeScreen.route)
            true
        }

        else -> {
            this.popBackStack()
            true
        }
    }
}
