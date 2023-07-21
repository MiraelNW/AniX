@file:OptIn(ExperimentalFoundationApi::class)

package com.miraelDev.hikari.presentation.AnimeListScreen.AnimeList

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.miraelDev.hikari.R
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.entensions.pressClickEffect
import com.miraelDev.hikari.ui.theme.Gold
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onThemeButtonClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAnimeItemClick: (Int) -> Unit
) {
    Box {

        val viewModel = hiltViewModel<AnimeListViewModel>()

        val screenState = viewModel.screenState.collectAsState(AnimeListScreenState.Initial)

        when (val currentState = screenState.value) {
            is AnimeListScreenState.Loading -> {}
            is AnimeListScreenState.AnimeList -> {
                AnimeList(
                    animeList = currentState.animes,
                    onSettingsClick = onSettingsClick,
                    onThemeButtonClick = onThemeButtonClick,
                    viewModel = viewModel,
                    onAnimeItemClick = onAnimeItemClick
                )
            }
            is AnimeListScreenState.Initial -> {}
        }

    }
}

@Composable
fun AnimeList(
    animeList: List<AnimeInfo>,
    onSettingsClick: () -> Unit,
    onThemeButtonClick: () -> Unit,
    viewModel: AnimeListViewModel,
    onAnimeItemClick: (Int) -> Unit
) {
    Column(
        Modifier
            .systemGestureExclusion()
            .padding(bottom = 48.dp)
    ) {

        var darkTheme by remember { mutableStateOf(false) }

        Toolbar(
            onSettingsClick = onSettingsClick,
            onThemeButtonClick = {
                darkTheme = !darkTheme
                onThemeButtonClick()
            },
            darkTheme = darkTheme
        )

        ScrollableTabWithViewPager(animeList, viewModel, onAnimeItemClick)
    }
}



