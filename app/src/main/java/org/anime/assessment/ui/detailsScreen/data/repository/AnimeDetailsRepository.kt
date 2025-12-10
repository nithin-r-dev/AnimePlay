package org.anime.assessment.ui.detailsScreen.data.repository

import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.database.classes.AnimeProductionDetails
import org.anime.assessment.ui.detailsScreen.data.local.DetailsScreenLocal

object AnimeDetailsRepository {
    /**
     * Repository method to call anime details from local module
     */
    fun getAnimeProdDetails(id: Int): List<AnimeProductionDetails> {
        return DetailsScreenLocal.getProductionDetails(id)
    }
    /**
     * Repository method to call anime details from local module
     */
    fun getAnimeDetails(id: Int): AnimeHomeClass? {
        return DetailsScreenLocal.getAnimeDetails(id)
    }
}