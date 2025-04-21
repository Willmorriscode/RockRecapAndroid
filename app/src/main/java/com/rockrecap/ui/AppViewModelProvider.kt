package com.rockrecap.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rockrecap.RockRecapApplication
import com.rockrecap.data.RouteViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for RouteViewModel
        initializer {
            RouteViewModel(
                rockRecapApplication().container.routesRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [RouteApplication].
 */
fun CreationExtras.rockRecapApplication(): RockRecapApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as RockRecapApplication)
