package com.miraelDev.vauma.navigation

sealed class Screen(val route: String) {

    object SearchAndFilter : Screen(ROUTE_SEARCH_AND_FILTER)

    object HomeAndSettings : Screen(ROUTE_HOME_AND_SETTINGS)
    object AnimeDetailAndVideoView : Screen(ROUTE_ANIME_DETAIL_AND_VIDEO_VIEW)

    object ProfileAndSettings : Screen(ROUTE_PROFILE_AND_SETTINGS)

    object Home : Screen(ROUTE_HOME)

    object Search : Screen(ROUTE_SEARCH)

    object Favourite : Screen(ROUTE_FAVOURITE)

    object Account : Screen(ROUTE_ACCOUNT)

    object Filter : Screen(ROUTE_FILTER)

    object EditProfile : Screen(ROUTE_EDIT_PROFILE)

    object Notifications : Screen(ROUTE_NOTIFICATIONS)

    object DownloadVideo : Screen(ROUTE_DOWNLOAD_VIDEO)

    object Language : Screen(ROUTE_LANGUAGE)

    object PrivacyPolicy : Screen(ROUTE_PRIVACY_POLICY)

    object ColorPalette : Screen(ROUTE_COLOR_PALETTE)

    object AnimeDetail : Screen(ROUTE_ANIME_DETAIL) {

        private const val ROUTE_FOR_ARGS = "anime_detail"

        fun getRouteWithArgs(animeId: Int): String {
            return "$ROUTE_FOR_ARGS/$animeId"
        }

    }

    object VideoView : Screen(ROUTE_VIDEO_VIEW)

    companion object {

        const val KEY_ANIME_DETAIL_ID = "anime_detail_id"

        private const val ROUTE_SEARCH_AND_FILTER = "search_and_filter"
        private const val ROUTE_HOME_AND_SETTINGS = "home_and_settings"
        private const val ROUTE_PROFILE_AND_SETTINGS = "profile_and_settings"
        private const val ROUTE_ANIME_DETAIL_AND_VIDEO_VIEW = "anime_detail_and_video_view"

        private const val ROUTE_HOME = "home"
        private const val ROUTE_FAVOURITE = "favourite"
        private const val ROUTE_ACCOUNT = "account"
        private const val ROUTE_SEARCH = "search"
        private const val ROUTE_FILTER = "filter"
        private const val ROUTE_SETTINGS = "settings"
        private const val ROUTE_EDIT_PROFILE = "edit_profile"
        private const val ROUTE_DOWNLOAD_VIDEO = "download_video"
        private const val ROUTE_NOTIFICATIONS = "notifications"
        private const val ROUTE_LANGUAGE = "language"
        private const val ROUTE_PRIVACY_POLICY = "privacy_policy"
        private const val ROUTE_COLOR_PALETTE = "color_palette"
        private const val ROUTE_ANIME_DETAIL = "anime_detail/{$KEY_ANIME_DETAIL_ID}"
        private const val ROUTE_VIDEO_VIEW = "video_view"

    }
}
