package org.anime.assessment.ui.detailsScreen.data.remote

import androidx.core.content.ContextCompat
import org.anime.assessment.R
import org.anime.assessment.apiServices.ApiClientInstance
import org.anime.assessment.application.MyApplication
import org.anime.assessment.constants.AppConstants.Companion.DETAILS_SCREEN_RESPONSE
import org.anime.assessment.constants.AppConstants.Companion.DETAILS_SCREEN_RESPONSE_ERROR
import org.anime.assessment.ui.detailsScreen.data.model.JikanDetailsResponse
import org.anime.assessment.utils.Utility
import org.anime.assessment.utils.interfaces.ResponseListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RemoteDetailsScreen {

    fun callAnimeDetails(id: Int, listener: ResponseListener) {
        ApiClientInstance.instance.callAnimeDetailsApi("v4/anime/$id").enqueue(object :
            Callback<JikanDetailsResponse> {
            override fun onResponse(
                call: Call<JikanDetailsResponse>,
                response: Response<JikanDetailsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    listener.onResponse(DETAILS_SCREEN_RESPONSE, response.body())
                } else {
                    listener.onResponse(
                        DETAILS_SCREEN_RESPONSE_ERROR,
                        Utility.parseErrorMessage(response.errorBody()) ?: ContextCompat.getString(
                            MyApplication.getInstance(),
                            R.string.error_while_fetching_details
                        )
                    )
                }
            }

            override fun onFailure(call: Call<JikanDetailsResponse>, t: Throwable) {
                listener.onResponse(
                    DETAILS_SCREEN_RESPONSE_ERROR,
                    t.message ?: ContextCompat.getString(
                        MyApplication.getInstance(),
                        R.string.error_while_fetching_details
                    )
                )
            }
        })
    }
}