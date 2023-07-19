@file:OptIn(ExperimentalFoundationApi::class)

package com.miraelDev.anix.presentation.AnimeListScreen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.miraelDev.anix.R
import com.miraelDev.anix.domain.models.AnimeInfo
import com.miraelDev.anix.entensions.pressClickEffect
import com.miraelDev.anix.ui.theme.Gold
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

@Composable
fun ScrollableTabWithViewPager(
    animeList: List<AnimeInfo>,
    viewModel: AnimeListViewModel,
    onAnimeItemClick: (Int) -> Unit
) {

    val pagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }

    val categoryList = listOf(
        stringResource(R.string.new_str),
        stringResource(R.string.popular),
        stringResource(R.string.name),
        stringResource(R.string.films)
    )

    ScrollableTabRow(
        modifier = Modifier.height(50.dp),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.background,
        edgePadding = 8.dp,
        divider = {},
        indicator = indicator
    ) {
        categoryList.forEachIndexed { index, title ->
            Tab(
                modifier = Modifier.zIndex(6f),
                text = {
                    Text(
                        text = title,
                        color = if (index == pagerState.currentPage) MaterialTheme.colors.onBackground
                        else MaterialTheme.colors.onBackground.copy(0.75f)
                    )
                },
                selected = index == pagerState.currentPage,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
            )
        }
    }

    HorizontalPager(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 36.dp, start = 4.dp, end = 4.dp)
            .systemGestureExclusion(),
        pageCount = categoryList.size,
        state = pagerState
    ) { page ->

        LaunchedEffect(Unit) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                when (page) {
                    0 -> {
                        viewModel.loadAnimeBtCategory(0)
                    }
                    1 -> {
                        viewModel.loadAnimeBtCategory(1)
                    }
                    2 -> {
                        viewModel.loadAnimeBtCategory(2)
                    }
                    3 -> {
                        viewModel.loadAnimeBtCategory(3)
                    }
                    else -> {}
                }
            }
        }


        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 4.dp,
                    bottom = 16.dp,
                    start = 4.dp,
                    end = 4.dp
                ),
                state = rememberLazyListState(),
                modifier = Modifier.navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = animeList, key = { it.id }) {
                    AnimeCard(animeItem = it, onAnimeItemClick = onAnimeItemClick)
                }
            }
        }

    }
}

@Composable
private fun Toolbar(
    onSettingsClick: () -> Unit,
    onThemeButtonClick: () -> Unit,
    darkTheme: Boolean
) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "AniX", color = MaterialTheme.colors.onBackground, fontSize = 24.sp)

            SettingsAndThemeButtons(
                onSettingsClick = onSettingsClick,
                onThemeButtonClick = onThemeButtonClick,
                darkTheme = darkTheme
            )

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SettingsAndThemeButtons(
    onSettingsClick: () -> Unit,
    onThemeButtonClick: () -> Unit,
    darkTheme: Boolean
) {
    Row {

        AnimatedContent(
            targetState = darkTheme,
            transitionSpec = {
                fadeIn(animationSpec = tween(150)) +
                        scaleIn(
                            initialScale = 0.4f,
                            animationSpec = tween(300)
                        ) with
                        fadeOut(animationSpec = tween(150))
            }
        ) { darkTheme ->

            val iconId = if (darkTheme) {
                R.drawable.ic_sun
            } else {
                R.drawable.ic_moon
            }

            IconButton(
                modifier = Modifier
                    .size(28.dp)
                    .padding(2.dp),
                onClick = { onThemeButtonClick() }
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Change theme"
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
                .width(12.dp)
        )

        IconButton(
            onClick = { onSettingsClick() },
            Modifier
                .size(28.dp)
                .padding(2.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Settings",
                modifier = Modifier.fillMaxSize(),
            )
        }

    }

}


@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage)
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .padding(2.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary, RoundedCornerShape(50))
            .zIndex(1f)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeCard(animeItem: AnimeInfo, onAnimeItemClick: (Int) -> Unit) {

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(700)
        )
    }
    val animatedModifier = Modifier.alpha(animatedProgress.value)

    Card(
        onClick = { onAnimeItemClick(animeItem.id) },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .pressClickEffect(),
        elevation = 4.dp
    ) {
        Row(modifier = animatedModifier) {
            AsyncImage(
                model = animeItem.image,
                contentDescription = animeItem.nameEn,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(
                modifier = Modifier
                    .height(150.dp)
                    .width(16.dp)
            )
            AnimePreview(animeItem = animeItem)


        }
    }
}

@Composable
private fun AnimePreview(animeItem: AnimeInfo) {
    Column(
        Modifier.padding(top = 4.dp, end = 8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = animeItem.nameEn, color = MaterialTheme.colors.onBackground)
            Rating(animeItem = animeItem)
        }
        Text(text = animeItem.nameRu, color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f))
        Spacer(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
        )
        Text(
            text = animeItem.description,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
private fun Rating(animeItem: AnimeInfo) {
    Row {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Rating",
            tint = Gold
        )
        Text(text = animeItem.score.toString(), color = MaterialTheme.colors.onBackground)
    }
}