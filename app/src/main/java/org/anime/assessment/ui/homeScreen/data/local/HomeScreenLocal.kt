package org.anime.assessment.ui.homeScreen.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.anime.assessment.application.MyApplication
import org.anime.assessment.database.AppDatabase
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.ui.homeScreen.data.model.JikanHomeScreenResponse
import org.anime.assessment.ui.homeScreen.data.model.toHomeEntity
import org.anime.assessment.ui.homeScreen.data.model.toProductionEntities
import org.anime.assessment.utils.Utility

object HomeScreenLocal {
    /**
     * Method to call Insertion method from API response
     * @param response Response body from API.
     */
    fun insertAnimeHomeList(response: JikanHomeScreenResponse?) {
        MyApplication.ioScope.launch {
            InsertHomeDetailsToLocalDb(response).execute()
        }
    }

    /**
     * Method to fetch Home screen list from database with offset
     * @param offset Integer value indicating offset data
     */
    fun getAnimeHomeList(offset: Int): List<AnimeHomeClass> {
        val appDatabase = AppDatabase.getDatabase(MyApplication.getInstance())
        return appDatabase.dao().getAnimeHomeList(offset)
    }
}

/**
 * Class which extends Executor service to do db operations in background
 * @param response [JikanHomeScreenResponse] response data class returned from API
 */
class InsertHomeDetailsToLocalDb(
    private val response: JikanHomeScreenResponse?
) {

    suspend fun execute() = withContext(Dispatchers.IO) {
        val appDatabase = AppDatabase.getDatabase(MyApplication.getInstance())

        try {
            val homeList = response?.data?.mapNotNull { data ->
                data?.toHomeEntity()
            } ?: emptyList()

            val productionList = response?.data?.flatMap { data ->
                data?.toProductionEntities() ?: emptyList()
            } ?: emptyList()

            if (homeList.isNotEmpty()) {
                appDatabase.dao().insertAnimeHomeList(homeList)
            }

            if (productionList.isNotEmpty()) {
                appDatabase.dao().insertProductionDetails(productionList)
            }

        } catch (e: Exception) {
            Utility.printLogConsole("##InsertHomeDetailsToLocalDb", "Error → ${e.message}")
        }
    }

}