package org.anime.assessment.apiServices

import androidx.room.Query
import org.anime.assessment.ui.detailsScreen.data.model.JikanDetailsResponse
import org.anime.assessment.ui.homeScreen.data.model.JikanHomeScreenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface APiInterface {

    @GET
    fun callAnimeHomeListApi(@Url url: String) : Call<JikanHomeScreenResponse>

    @GET
    fun callAnimeDetailsApi(@Url url: String): Call<JikanDetailsResponse>
}