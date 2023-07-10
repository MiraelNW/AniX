package com.miraelDev.anix.navigation

import android.util.Log
import com.miraelDev.anix.domain.models.AnimeInfo

sealed class Screen(val route : String){

    object Home:Screen(ROUTE_HOME)

    object SearchAndFilter:Screen(ROUTE_SEARCH_AND_FILTER)

    object HomeAndSettings:Screen(ROUTE_HOME_AND_SETTINGS)

    object DifferentSettings:Screen(ROUTE_DIFFERENT_SETTINGS)

    object Library:Screen(ROUTE_LIBRARY)

    object Filter:Screen(ROUTE_FILTER)

    object Search:Screen(ROUTE_SEARCH)

    object Settings:Screen(ROUTE_SETTINGS)

    object Notifications:Screen(ROUTE_NOTIFICATIONS)

    object Language:Screen(ROUTE_LANGUAGE)

    object PrivacyPolicy:Screen(ROUTE_PRIVACY_POLICY)

    object ColorPalette:Screen(ROUTE_COLOR_PALETTE)

    object AnimeDetail:Screen(ROUTE_ANIME_DETAIL){

        private const val ROUTE_FOR_ARGS = "anime_detail"

        fun getRouteWithArgs(animeId: Int): String {
            return "$ROUTE_FOR_ARGS/$animeId"
        }

    }

    companion object{

        const val KEY_ANIME_DETAIL_ID = "anime_detail_id"

        private const val ROUTE_SEARCH_AND_FILTER = "search_and_filter"
        private const val ROUTE_HOME_AND_SETTINGS = "home_and_settings"
        private const val ROUTE_DIFFERENT_SETTINGS = "different_settings"

        private const val ROUTE_HOME = "home"
        private const val ROUTE_LIBRARY = "library"
        private const val ROUTE_SEARCH = "search"
        private const val ROUTE_FILTER = "filter"
        private const val ROUTE_SETTINGS = "settings"
        private const val ROUTE_NOTIFICATIONS = "notifications"
        private const val ROUTE_LANGUAGE = "language"
        private const val ROUTE_PRIVACY_POLICY = "privacy_policy"
        private const val ROUTE_COLOR_PALETTE = "color_palette"
        private const val ROUTE_ANIME_DETAIL = "anime_detail/{$KEY_ANIME_DETAIL_ID}"

    }
}
