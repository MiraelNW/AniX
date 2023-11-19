package com.miraelDev.vauma.navigation

sealed class Screen(val route: String) {

    object SignIn : Screen(ROUTE_SIGN_IN)

    object SignUp : Screen(ROUTE_SIGN_UP)

    object EmailChoose : Screen(ROUTE_EMAIL_CHOOSE)

    object SearchAndFilter : Screen(ROUTE_SEARCH_AND_FILTER)

    object SignUpAndCodeVerify : Screen(ROUTE_SIGN_UP_AND_CODE_VERIFY)

    object SignInAndResetPassword : Screen(ROUTE_SIGN_IN_AND_RESET_PASSWORD)

    object ResetPasswordGraph : Screen(ROUTE_RESET_PASSWORD_GRAPH)

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

    object ResetPassword : Screen(ROUTE_RESET_PASSWORD){
        private const val ROUTE_FOR_ARGS = "reset_password"

        fun getRouteWithArgs(email: String): String {
            return "${ROUTE_FOR_ARGS}/$email"
        }
    }

    object EmailCodeVerify : Screen(ROUTE_EMAIL_CODE_VERIFY) {

        private const val ROUTE_FOR_ARGS = "email_code_verify"

        fun getRouteWithArgs(email: String, password: String): String {
            return "$ROUTE_FOR_ARGS/$email/$password"
        }

    }

    object EmailCodeVerifyResetPassword : Screen(ROUTE_EMAIL_CODE_VERIFY_RESET_PASSWORD) {

        private const val ROUTE_FOR_ARGS = "email_code_verify_reset_password"

        fun getRouteWithArgs(email: String): String {
            return "$ROUTE_FOR_ARGS/$email"
        }

    }

    object AnimeDetail : Screen(ROUTE_ANIME_DETAIL) {

        private const val ROUTE_FOR_ARGS = "anime_detail"

        fun getRouteWithArgs(animeId: Int): String {
            return "$ROUTE_FOR_ARGS/$animeId"
        }

    }

    object VideoView : Screen(ROUTE_VIDEO_VIEW)

    companion object {

        const val KEY_ANIME_DETAIL_ID = "anime_detail_id"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"

        private const val ROUTE_SIGN_IN = "sign_in"
        private const val ROUTE_SIGN_UP = "sign_up"
        private const val ROUTE_RESET_PASSWORD_GRAPH = "reset_password_graph"
        private const val ROUTE_EMAIL_CHOOSE = "email_choose"
        private const val ROUTE_SEARCH_AND_FILTER = "search_and_filter"
        private const val ROUTE_SIGN_UP_AND_CODE_VERIFY = "sign_up_and_code_verify"
        private const val ROUTE_SIGN_IN_AND_RESET_PASSWORD = "sign_in_and_reset_password"
        private const val ROUTE_HOME_AND_SETTINGS = "home_and_settings"
        private const val ROUTE_PROFILE_AND_SETTINGS = "profile_and_settings"
        private const val ROUTE_ANIME_DETAIL_AND_VIDEO_VIEW = "anime_detail_and_video_view"

        private const val ROUTE_HOME = "home"
        private const val ROUTE_FAVOURITE = "favourite"
        private const val ROUTE_ACCOUNT = "account"
        private const val ROUTE_SEARCH = "search"
        private const val ROUTE_FILTER = "filter"
        private const val ROUTE_EDIT_PROFILE = "edit_profile"
        private const val ROUTE_DOWNLOAD_VIDEO = "download_video"
        private const val ROUTE_NOTIFICATIONS = "notifications"
        private const val ROUTE_LANGUAGE = "language"
        private const val ROUTE_PRIVACY_POLICY = "privacy_policy"
        private const val ROUTE_ANIME_DETAIL = "anime_detail/{$KEY_ANIME_DETAIL_ID}"
        private const val ROUTE_EMAIL_CODE_VERIFY = "email_code_verify/{$KEY_EMAIL}/{$KEY_PASSWORD}"
        private const val ROUTE_RESET_PASSWORD = "reset_password/{$KEY_EMAIL}"
        private const val ROUTE_EMAIL_CODE_VERIFY_RESET_PASSWORD =
            "email_code_verify_reset_password/{$KEY_EMAIL}"
        private const val ROUTE_VIDEO_VIEW = "video_view"

    }
}
