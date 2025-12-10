package org.anime.assessment.ui.homeScreen.data.repository

import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.ui.homeScreen.data.local.HomeScreenLocal
import org.anime.assessment.ui.homeScreen.data.remote.RemoteHomeScreen
import org.anime.assessment.utils.interfaces.ResponseListener

object HomeScreenRepository {

    // Remote Access
    fun callAnimeHomeList(page: Int, listener: ResponseListener) {
        RemoteHomeScreen.callAnimeHomeList(page, listener)
    }


    // Local access
    /**
     * Method to fetch Home screen list from Local package with offset
     * @param offset Integer value indicating offset data
     */
    fun getAnimeHomeList(offset: Int): List<AnimeHomeClass> {
        return HomeScreenLocal.getAnimeHomeList(offset)
    }
}