package org.anime.assessment.ui.homeScreen.data.model

import com.google.gson.annotations.SerializedName
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.database.classes.AnimeProductionDetails
import org.anime.assessment.utils.DateUtils
import org.anime.assessment.utils.DateUtils.callDateFormatChangeMethod

data class JikanHomeScreenResponse(

    @field:SerializedName("pagination")
    val pagination: Pagination? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null
)

data class GenresItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class Broadcast(

    @field:SerializedName("string")
    val string: String? = null,

    @field:SerializedName("timezone")
    val timezone: String? = null,

    @field:SerializedName("time")
    val time: String? = null,

    @field:SerializedName("day")
    val day: String? = null
)

data class StudiosItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class Images(

    @field:SerializedName("jpg")
    val jpg: Jpg? = null,

    @field:SerializedName("webp")
    val webp: Webp? = null,

    @field:SerializedName("large_image_url")
    val largeImageUrl: String? = null,

    @field:SerializedName("small_image_url")
    val smallImageUrl: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("medium_image_url")
    val mediumImageUrl: Any? = null,

    @field:SerializedName("maximum_image_url")
    val maximumImageUrl: Any? = null
)

data class Aired(

    @field:SerializedName("string")
    val string: String? = null,

    @field:SerializedName("prop")
    val prop: Prop? = null,

    @field:SerializedName("from")
    val from: String? = null,

    @field:SerializedName("to")
    val to: String? = null
)

data class Pagination(

    @field:SerializedName("has_next_page")
    val hasNextPage: Boolean? = null,

    @field:SerializedName("last_visible_page")
    val lastVisiblePage: Int? = null,

    @field:SerializedName("items")
    val items: Items? = null,

    @field:SerializedName("current_page")
    val currentPage: Int? = null
)

data class To(

    @field:SerializedName("month")
    val month: Int? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("day")
    val day: Int? = null
)

data class Prop(

    @field:SerializedName("from")
    val from: From? = null,

    @field:SerializedName("to")
    val to: To? = null
)

data class Items(

    @field:SerializedName("per_page")
    val perPage: Int? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("count")
    val count: Int? = null
)

data class Trailer(

    @field:SerializedName("images")
    val images: Images? = null,

    @field:SerializedName("embed_url")
    val embedUrl: String? = null,

    @field:SerializedName("youtube_id")
    val youtubeId: Any? = null,

    @field:SerializedName("url")
    val url: Any? = null
)

data class TitlesItem(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("title")
    val title: String? = null
)

data class From(

    @field:SerializedName("month")
    val month: Int? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("day")
    val day: Int? = null
)

data class DemographicsItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class Webp(

    @field:SerializedName("large_image_url")
    val largeImageUrl: String? = null,

    @field:SerializedName("small_image_url")
    val smallImageUrl: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null
)

data class LicensorsItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class Jpg(

    @field:SerializedName("large_image_url")
    val largeImageUrl: String? = null,

    @field:SerializedName("small_image_url")
    val smallImageUrl: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null
)

data class ProducersItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class DataItem(

    @field:SerializedName("title_japanese")
    val titleJapanese: String? = null,

    @field:SerializedName("favorites")
    val favorites: Int? = null,

    @field:SerializedName("broadcast")
    val broadcast: Broadcast? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("scored_by")
    val scoredBy: Int? = null,

    @field:SerializedName("title_synonyms")
    val titleSynonyms: List<String?>? = null,

    @field:SerializedName("source")
    val source: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("trailer")
    val trailer: Trailer? = null,

    @field:SerializedName("duration")
    val duration: String? = null,

    @field:SerializedName("score")
    val score: String? = null,

    @field:SerializedName("themes")
    val themes: List<Any?>? = null,

    @field:SerializedName("approved")
    val approved: Boolean? = null,

    @field:SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @field:SerializedName("popularity")
    val popularity: Int? = null,

    @field:SerializedName("members")
    val members: Int? = null,

    @field:SerializedName("title_english")
    val titleEnglish: String? = null,

    @field:SerializedName("rank")
    val rank: Int? = null,

    @field:SerializedName("season")
    val season: String? = null,

    @field:SerializedName("airing")
    val airing: Boolean? = null,

    @field:SerializedName("episodes")
    val episodes: Int? = null,

    @field:SerializedName("aired")
    val aired: Aired? = null,

    @field:SerializedName("images")
    val images: Images? = null,

    @field:SerializedName("studios")
    val studios: List<StudiosItem?>? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("titles")
    val titles: List<TitlesItem?>? = null,

    @field:SerializedName("synopsis")
    val synopsis: String? = null,

    @field:SerializedName("explicit_genres")
    val explicitGenres: List<Any?>? = null,

    @field:SerializedName("licensors")
    val licensors: List<LicensorsItem?>? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("producers")
    val producers: List<ProducersItem?>? = null,

    @field:SerializedName("background")
    val background: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("demographics")
    val demographics: List<DemographicsItem?>? = null
)

data class ThemesItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

/**
 * Extension Method to map [DataItem] class to Local DB entity class [AnimeHomeClass]
 */
fun DataItem.toHomeEntity(): AnimeHomeClass {
    val id = malId ?: 0

    return AnimeHomeClass(
        id = id,
        name = title.orEmpty(),
        rating = rating.orEmpty(),
        episodes = episodes ?: 0,
        score = score.orEmpty(),
        season = season.orEmpty(),
        year = year ?: 0,
        rank = rank ?: 0,
        airingStatus = status.orEmpty(),
        isAiring = airing ?: false,
        type = type ?: "",
        imageUrl = images?.jpg?.imageUrl.orEmpty(),
        largeImageUrl = images?.jpg?.largeImageUrl.orEmpty(),
        videoUrl = trailer?.embedUrl.orEmpty(),
        url = url.orEmpty(),
        synopsis = synopsis.orEmpty(),
        lastAired = callDateFormatChangeMethod(
            aired?.to ?: aired?.from ?: "",
            DateUtils.ISO_8601_FORMAT,
            DateUtils.NORMAL_FORMAT
        ),
        duration = duration.orEmpty()
    )
}

/**
 * Extension Method to map [DataItem] class to Local DB entity class [AnimeProductionDetails]
 */
fun DataItem.toProductionEntities(): List<AnimeProductionDetails> {
    val animeId = malId ?: 0

    val studioList = studios?.map { studio ->
        AnimeProductionDetails(
            id = (studio?.malId ?: 0) + animeId,
            animeID = animeId,
            name = studio?.name.orEmpty(),
            type = studio?.type.orEmpty(),
            dataType = 1
        )
    } ?: emptyList()

    val genreList = genres?.map { genre ->
        AnimeProductionDetails(
            id = (genre?.malId ?: 0) + animeId,
            animeID = animeId,
            name = genre?.name.orEmpty(),
            type = genre?.type.orEmpty(),
            dataType = 2
        )
    } ?: emptyList()

    val producerList = producers?.map { prod ->
        AnimeProductionDetails(
            id = (prod?.malId ?: 0) + animeId,
            animeID = animeId,
            name = prod?.name.orEmpty(),
            type = prod?.type.orEmpty(),
            dataType = 3
        )
    } ?: emptyList()

    return studioList + genreList + producerList
}