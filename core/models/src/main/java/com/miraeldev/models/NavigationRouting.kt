package com.miraeldev.models

import com.miraeldev.models.anime.Settings

typealias OnLogOut = () -> Unit
typealias LogIn = () -> Unit
typealias OnSignUp = (String, String) -> Unit
typealias OnSignUpClicked = () -> Unit
typealias OnForgetPasswordClick = () -> Unit
typealias OnAnimeItemClick = (Int) -> Unit
typealias ShowSearchHistory = () -> Unit
typealias SearchAnimeByName = (String) -> Unit
typealias OnSeeAllClick = (Int) -> Unit
typealias OnPlayClick = (Int) -> Unit
typealias OnFilterClick = () -> Unit
typealias OnShowSearchHistory = () -> Unit
typealias ShowInitialList = () -> Unit
typealias OnBackPressed = () -> Unit
typealias OnOtpVerified = () -> Unit
typealias OnSeriesClick = () -> Unit
typealias NavigateToSearchScreen = (String) -> Unit
typealias OnEmailExist = (String) -> Unit
typealias OnSettingClick = (Settings) -> Unit
