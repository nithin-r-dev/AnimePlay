package org.anime.assessment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.database.classes.AnimeProductionDetails

@Dao
interface AniDao {
    // Home List Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimeHomeList(list: List<AnimeHomeClass>)

    @Query("Select * From AnimeHomeClass ORDER BY rank ASC Limit 20 OFFSET :offset")
    fun getAnimeHomeList(offset: Int): List<AnimeHomeClass>

    @Query("Select * From AnimeHomeClass Where id = :id")
    fun getAnimeHomeWithID(id: Int): AnimeHomeClass?


    // Production Details Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductionDetails(productionList: List<AnimeProductionDetails>)

    @Query("Select * From AnimeProductionDetails Where AnimeID =:id")
    fun getProductionDetailsList(id: Int): List<AnimeProductionDetails>
}