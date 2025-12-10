package org.anime.assessment.database.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AnimeProductionDetails")
data class AnimeProductionDetails(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "AnimeID") val animeID: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "dataType") val dataType: Int?, // 1--> for studio details, 2--> for genere details, 3---> for producers details
)