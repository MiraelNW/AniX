package com.miraeldev.detailinfo.presentation

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.R
import com.miraeldev.presentation.FavouriteIcon
import com.miraeldev.presentation.shimmerList.ShimmerListAnimeDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val CLOSE_BOTTOM_SHEET = 0
private const val RATING_SCREEN = 1
private const val DOWNLOAD_SCREEN = 2

@Composable
fun AnimeDetailScreen(
    animeId: Int,
    onBackPressed: () -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    onSeriesClick: () -> Unit
) {
    val viewModel = hiltViewModel<AnimeDetailViewModel>()

    val screenState by viewModel.animeDetail.collectAsStateWithLifecycle(AnimeDetailScreenState.Initial)

    val onBackPressedAction = remember { { onBackPressed() } }

    val loadAnimeDetailAction: (Int) -> Unit = remember { { viewModel.loadAnimeDetail(it) } }
    val loadAnimeVideoAction: (Int) -> Unit = remember { { viewModel.loadVideoId(it) } }
    val downloadEpisodeAction: (String, String) -> Unit =
        remember { { url, videoName -> viewModel.downloadEpisode(url, videoName) } }


    var shouldShowLoading by remember {
        mutableStateOf(false)
    }

    BackHandler(onBack = onBackPressedAction)

    when (val results = screenState) {

        is AnimeDetailScreenState.SearchResult -> {

            val selectAnimeItemAction: (Boolean) -> Unit =
                remember {
                    { isSelected -> viewModel.selectAnimeItem(isSelected, results.result.first()) }
                }

            DetailScreen(
                animeDetail = results.result.first(),
                onBackPressed = onBackPressedAction,
                onAnimeItemClick = onAnimeItemClick,
                onSeriesClick = { videoId ->
                    loadAnimeVideoAction(videoId)
                    onSeriesClick()
                },
                downloadAnimeEpisode = downloadEpisodeAction,
                onFavouriteIconCLick = selectAnimeItemAction

            )
        }

        is AnimeDetailScreenState.SearchFailure -> {
        }

        is AnimeDetailScreenState.Loading -> {
            loadAnimeDetailAction(animeId)

            LaunchedEffect(key1 = Unit) {
                delay(200)
                shouldShowLoading = true
            }
            if (shouldShowLoading) {
                ShimmerListAnimeDetail()
            }
        }

        is AnimeDetailScreenState.Initial -> {}
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DetailScreen(
    animeDetail: AnimeDetailInfo,
    onBackPressed: () -> Unit,
    onSeriesClick: (Int) -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    downloadAnimeEpisode: (String, String) -> Unit,
    onFavouriteIconCLick: (Boolean) -> Unit,
) {

    var showSeriesDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    var currentBottomSheetScreen by rememberSaveable { mutableIntStateOf(CLOSE_BOTTOM_SHEET) }

    BottomSheet(
        bottomSheetScreen = currentBottomSheetScreen,
        animeDetailInfo = animeDetail,
        onBackPressed = {
            coroutineScope.launch { modalSheetState.hide() }
        },
        modalSheetState = modalSheetState,
        onDownloadClick = { selectedList ->
            selectedList.forEach {
                downloadAnimeEpisode(
                    animeDetail.videoUrls.playerUrl[it],
                    animeDetail.videoUrls.videoName[it]
                )
            }
            coroutineScope.launch {
                modalSheetState.hide()
            }
        },
        onCloseDownloadSheet = {
            coroutineScope.launch {
                currentBottomSheetScreen = CLOSE_BOTTOM_SHEET
                modalSheetState.hide()
            }
        }
    ) {
        Box(
            modifier = Modifier.navigationBarsPadding()
        ) {

            LazyColumn(
                modifier = Modifier
                    .navigationBarsPadding(),
            ) {

                item { TopAnimeImage(animeItem = animeDetail) }

                item {
                    AnimeNameAndShareButton(
                        animeItem = animeDetail,
                        onShareButtonClick = {

                            val intent = Intent()
                            intent.action = Intent.ACTION_SEND
                            intent.putExtra(
                                Intent.EXTRA_TEXT,
                                "Привет! \n" +
                                        "Смотри какое аниме я нашел:\n " +
                                        "${animeDetail.nameRu} \n" +
                                        "в нем ${animeDetail.episodes} серий \n" +
                                        "обязательно посмотри его в приложении Vauma https://vauma.com/anime-detail/${animeDetail.id}"
                            )
                            intent.type = "text/plain"
                            startActivity(
                                context,
                                Intent.createChooser(
                                    intent,
                                    "Share To:"
                                ),
                                null
                            )
                        }
                    )
                }

                item {
                    PlayButton(
                        onPlayClick = { showSeriesDialog = true },
                        onDownloadClick = {
                            coroutineScope.launch {
                                currentBottomSheetScreen = DOWNLOAD_SCREEN
                                modalSheetState.show()
                            }
                        }
                    )
                }

                item {
                    RatingAndCategoriesRow(
                        animeItem = animeDetail,
                        onRatingClick = {
                            coroutineScope.launch {
                                currentBottomSheetScreen = RATING_SCREEN
                                modalSheetState.show()
                            }
                        }
                    )
                }

                item { GenreRow(animeItem = animeDetail) }

                item { ExpandableDescription(text = animeDetail.description) }

                item {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.watch_others),
                        fontSize = 24.sp
                    )
                }

                item {
                    OtherAnime(
                        animeList = animeDetail.similar,
                        onAnimeItemClick = onAnimeItemClick
                    )
                }
            }
            BackIcon(onBackPressed = onBackPressed)

            FavouriteIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(end = 8.dp, top = 8.dp),
                onFavouriteIconClick = onFavouriteIconCLick
            )

            if (showSeriesDialog) {
                AnimeSeriesDialog(
                    onDismiss = {
                        showSeriesDialog = false
                    },
                    onSeriesClick = onSeriesClick
                )
            }
        }
    }


}







