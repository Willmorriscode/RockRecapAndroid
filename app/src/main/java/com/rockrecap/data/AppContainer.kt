package com.rockrecap.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val routesRepository: RoutesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineRouteRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [RoutesRepository]
     */
    override val routesRepository: RoutesRepository by lazy {
        OfflineRouteRepository(RockRecapDatabase.getDatabase(context).routeDao())
    }
}