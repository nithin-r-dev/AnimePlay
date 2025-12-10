package org.anime.assessment.ui.detailsScreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.anime.assessment.R
import org.anime.assessment.application.MyApplication
import org.anime.assessment.constants.AppConstants.Companion.SELECTED_ANIME_ID
import org.anime.assessment.database.classes.AnimeHomeClass
import org.anime.assessment.database.classes.AnimeProductionDetails
import org.anime.assessment.ui.detailsScreen.data.repository.AnimeDetailsRepository
import org.anime.assessment.utils.PreferencesManager
import org.anime.assessment.utils.Utility


class AnimeDetailsViewModel : ViewModel() {
    val preferencesManager = PreferencesManager.getInstance(MyApplication.getInstance())
    private val _animeDetails = MutableStateFlow<AnimeHomeClass?>(null)
    val animeDetails: StateFlow<AnimeHomeClass?> get() = _animeDetails

    private val _productionDetails = MutableStateFlow<List<AnimeProductionDetails>>(listOf())
    val productionDetails: StateFlow<List<AnimeProductionDetails>> get() = _productionDetails

    private val _genreDetails = MutableStateFlow<List<AnimeProductionDetails>>(listOf())
    val genreDetails: StateFlow<List<AnimeProductionDetails>> get() = _genreDetails

    private val _studioDetails = MutableStateFlow<List<AnimeProductionDetails>>(listOf())
    val studioDetails: StateFlow<List<AnimeProductionDetails>> get() = _studioDetails

    private val _showTrailer = MutableStateFlow(false)
    val showTrailer: StateFlow<Boolean> get() = _showTrailer

    private val _alert = MutableStateFlow("")
    val alert: StateFlow<String> get() = _alert

    /**
     * Method to get production details
     */
    fun getProdDetails(id: Int) {
        viewModelScope.launch {
            try {
                _animeDetails.value = AnimeDetailsRepository.getAnimeDetails(
                    preferencesManager?.getIntValue(SELECTED_ANIME_ID) ?: 0
                )
                val details = AnimeDetailsRepository.getAnimeProdDetails(id)
                _genreDetails.value = details.filter { it.dataType == 2 }
                Utility.printLogConsole(
                    "##_genreDetails",
                    "_genreDetails-->${_genreDetails.value.size}"
                )

                _studioDetails.value = details.filter { it.dataType == 1 }
                _productionDetails.value = details.filter { it.dataType == 3 }
            } catch (e: Exception) {
                Utility.printLogConsole("##getProd", "error-->${e.message}")
            }
        }


    }

    /**
     * Method to check internet connection and open floating youtube player
     * @param value boolean value indicating visibility of floating view
     */
    fun toggleTrailerView(value: Boolean) {
        if (value) {
            if (Utility.isNetworkAvailable(MyApplication.getInstance())) {
                _showTrailer.value = true
            } else {
                updateAlert(MyApplication.getInstance().getString(R.string.connection_lost))
            }
        } else {
            _showTrailer.value = false
        }

    }

    /**
     * Method to update alter text view to state variables
     */
    fun updateAlert(value: String) {
        _alert.value = value
    }

}