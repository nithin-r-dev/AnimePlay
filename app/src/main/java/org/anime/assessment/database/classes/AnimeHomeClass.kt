package org.anime.assessment.database.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AnimeHomeClass")
data class AnimeHomeClass(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "episodes") val episodes: Int? = null,
    @ColumnInfo(name = "synopsis") val synopsis: String? = null,
    @ColumnInfo(name = "season") val season: String? = null,
    @ColumnInfo(name = "year") val year: Int? = null,
    @ColumnInfo(name = "type") val type: String? = null,

    //Ranking or rating details
    @ColumnInfo(name = "rating") val rating: String? = null,
    @ColumnInfo(name = "score") val score: String? = null,
    @ColumnInfo(name = "rank") val rank: Int? = null,
    //Airing details
    @ColumnInfo(name = "airingStatus") val airingStatus: String? = null,
    @ColumnInfo(name = "isAiring") val isAiring: Boolean? = null,
    @ColumnInfo(name = "lastAired") val lastAired: String? = null,
    @ColumnInfo(name = "duration") val duration: String? = null,
    //Urls
    @ColumnInfo(name = "imageUrl") val imageUrl: String? = null,
    @ColumnInfo(name = "largeImageUrl") val largeImageUrl: String? = null,
    @ColumnInfo(name = "videoUrl") val videoUrl: String? = null,
    @ColumnInfo(name = "url") val url: String? = null,
)