package org.anime.assessment.ui.detailsScreen.data.local

import org.anime.assessment.application.MyApplication
import org.anime.assessment.database.AppDatabase
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.database.classes.AnimeProductionDetails

object DetailsScreenLocal {
    /**
     * Method to get anime production details from local db
     * @param id Anime ID for which details are needed.
     */
    fun getProductionDetails(id: Int): List<AnimeProductionDetails> {
        return AppDatabase.getDatabase(MyApplication.getInstance()).dao()
            .getProductionDetailsList(id)

    }

    /**
     * Method to get anime home details from local db
     * @param id Anime ID for which details are needed.
     */
    fun getAnimeDetails(id: Int): AnimeHomeClass? {
        return AppDatabase.getDatabase(MyApplication.getInstance()).dao()
            .getAnimeHomeWithID(id)

    }
}