package org.anime.assessment.ui.homeScreen.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.anime.assessment.application.MyApplication
import org.anime.assessment.constants.AppConstants
import org.anime.assessment.constants.AppConstants.Companion.SELECTED_ANIME_ID
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.ui.homeScreen.data.model.JikanHomeScreenResponse
import org.anime.assessment.ui.homeScreen.data.model.toHomeEntity
import org.anime.assessment.ui.homeScreen.data.repository.HomeScreenRepository
import org.anime.assessment.utils.PreferencesManager
import org.anime.assessment.utils.Utility
import org.anime.assessment.utils.interfaces.ResponseListener

class HomeScreenViewModel : ViewModel() {
    private val preferencesManager = PreferencesManager.getInstance(MyApplication.getInstance())

    private val _homeList = MutableStateFlow<List<AnimeHomeClass>>(listOf())
    val homeList: StateFlow<List<AnimeHomeClass>> get() = _homeList

    private val _alertMessage = MutableStateFlow("")
    val alertMessage: StateFlow<String> get() = _alertMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private var offset by mutableIntStateOf(0)
    private var page by mutableIntStateOf(1)

    private var loadedFromDb by mutableStateOf(false)

    var initiated by mutableStateOf(false)

    //Interface listener to listen to response from API
    private val listener by mutableStateOf(object : ResponseListener {
        override fun onResponse(action: Int, data: Any?) {
            if (action == AppConstants.HOME_SCREEN_RESPONSE) {
                val response = data as JikanHomeScreenResponse?
                if (response != null) {
                    _homeList.update { list ->
                        list + (response.data?.mapNotNull { data ->
                            data?.toHomeEntity()
                        } ?: emptyList())
                    }
                }
                page += 1
                initiated = true
            } else {
                val message = data as String
                _alertMessage.value = message
            }
            _isLoading.value = false
        }
    })

    /**
     * Method to initialize and populate data from API or local db based on network connection availability.
     */
    fun initializeView(context: Context) {
        populateWithBdOrApi(context)
    }

    /**
     * Method to call db or api to load UI based on network connectivity
     */
    private fun populateWithBdOrApi(context: Context) {
        if (Utility.isNetworkAvailable(context)) {
            if (loadedFromDb) {
                _homeList.value = listOf()

            }
            _isLoading.value = true
            loadedFromDb = false
            HomeScreenRepository.callAnimeHomeList(page = page, listener)
        } else {
            if (!loadedFromDb) {
                _homeList.value = listOf()
            }
            loadedFromDb = true
            _homeList.update { list ->
                list + HomeScreenRepository.getAnimeHomeList(offset)
            }
            offset += _homeList.value.size
            _isLoading.value = false
            initiated = true
        }
    }

    /**
     * Method to set alert message state variable
     */
    fun updateAlertMessage(message: String) {
        _alertMessage.value = message
    }

    /**
     * Method to trigger load more and populate data with offset or page increase
     */
    fun loadMore(context: Context) {
        _isLoading.value = true
        populateWithBdOrApi(context)
    }

    fun updateSelectedAnimeToPref(id: Int) {
        preferencesManager?.setIntValue(SELECTED_ANIME_ID, id)
    }
}