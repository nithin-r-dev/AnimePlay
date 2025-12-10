package org.anime.assessment.ui.homeScreen.data.remote

import androidx.core.content.ContextCompat
import org.anime.assessment.R
import org.anime.assessment.apiServices.ApiClientInstance
import org.anime.assessment.application.MyApplication
import org.anime.assessment.constants.AppConstants.Companion.HOME_SCREEN_RESPONSE
import org.anime.assessment.constants.AppConstants.Companion.HOME_SCREEN_RESPONSE_ERROR
import org.anime.assessment.ui.homeScreen.data.local.HomeScreenLocal
import org.anime.assessment.ui.homeScreen.data.model.JikanHomeScreenResponse
import org.anime.assessment.utils.Utility
import org.anime.assessment.utils.interfaces.ResponseListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RemoteHomeScreen {
    /**
     * Method to call api service to get Anime List details from Get APi
     * @param page  integer value indicating page to be fetched.
     * @param listener Interface to communicate api response
     */
    fun callAnimeHomeList(page: Int, listener: ResponseListener) {
        ApiClientInstance.instance.callAnimeHomeListApi("v4/top/anime?page=$page")
            .enqueue(object :
                Callback<JikanHomeScreenResponse> {
                override fun onResponse(
                    call: Call<JikanHomeScreenResponse>,
                    response: Response<JikanHomeScreenResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        HomeScreenLocal.insertAnimeHomeList(response.body())
                        listener.onResponse(HOME_SCREEN_RESPONSE, response.body())
                    } else {
                        listener.onResponse(
                            HOME_SCREEN_RESPONSE_ERROR,
                            Utility.parseErrorMessage(response.errorBody())
                                ?: ContextCompat.getString(
                                    MyApplication.getInstance(),
                                    R.string.error_while_fetching_details
                                )
                        )
                    }
                }

                override fun onFailure(call: Call<JikanHomeScreenResponse>, t: Throwable) {
                    listener.onResponse(
                        HOME_SCREEN_RESPONSE_ERROR,
                        t.message ?: ContextCompat.getString(
                            MyApplication.getInstance(),
                            R.string.error_while_fetching_details
                        )
                    )
                }
            })
    }
}