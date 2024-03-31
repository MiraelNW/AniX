package com.miraeldev.detailinfo.presentation

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.designsystem.FavouriteIcon
import com.miraeldev.designsystem.shimmerlist.ShimmerListAnimeDetail
import com.miraeldev.detailinfo.R
import com.miraeldev.detailinfo.presentation.detailComponent.DetailComponent
import com.miraeldev.detailinfo.presentation.detailComponent.DetailStore
import com.miraeldev.detailinfo.presentation.ui.AnimeNameAndShareButton
import com.miraeldev.detailinfo.presentation.ui.AnimeSeriesDialog
import com.miraeldev.detailinfo.presentation.ui.BackIcon
import com.miraeldev.detailinfo.presentation.ui.BottomSheet
import com.miraeldev.detailinfo.presentation.ui.ExpandableDescription
import com.miraeldev.detailinfo.presentation.ui.GenreRow
import com.miraeldev.detailinfo.presentation.ui.OtherAnime
import com.miraeldev.detailinfo.presentation.ui.PlayButton
import com.miraeldev.detailinfo.presentation.ui.RatingAndCategoriesRow
import com.miraeldev.detailinfo.presentation.ui.TopAnimeImage
import com.miraeldev.detailinfo.presentation.ui.ZoomableImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val CLOSE_BOTTOM_SHEET = 0
private const val RATING_SCREEN = 1
private const val DOWNLOAD_SCREEN = 2

@Composable
fun AnimeDetailScreen(
    component: DetailComponent,
    imageLoader: VaumaImageLoader,
    animeId: Int
) {

    val model by component.model.collectAsStateWithLifecycle()

    var shouldShowLoading by remember { mutableStateOf(false) }

    BackHandler(onBack = component::onBackClicked)

    Box(modifier = Modifier.fillMaxSize()) {
        when (val results = model.animeDetailScreenState) {

            is DetailStore.State.AnimeDetailScreenState.SearchResult -> {

                DetailScreen(
                    imageLoader = imageLoader,
                    animeDetail = results.result.first(),
                    onBackPressed = component::onBackClicked,
                    onAnimeItemClick = component::onAnimeItemClick,
                    onSeriesClick = { videoId ->
                        component.loadAnimeVideo(results.result.first(), videoId)
                        component.onSeriesClick()
                    },
                    downloadAnimeEpisode = component::downloadEpisode,
                    onFavouriteIconCLick = {
                        component.selectAnimeItem(
                            isSelected = it,
                            animeInfo = results.result.first()
                        )
                    }

                )
            }

            is DetailStore.State.AnimeDetailScreenState.SearchFailure -> {
            }

            is DetailStore.State.AnimeDetailScreenState.Loading -> {
                component.loadAnimeDetail(animeId)

                LaunchedEffect(key1 = Unit) {
                    delay(200)
                    shouldShowLoading = true
                }
                if (shouldShowLoading) {
                    ShimmerListAnimeDetail()
                }
            }

            is DetailStore.State.AnimeDetailScreenState.Initial -> {}
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DetailScreen(
    imageLoader: VaumaImageLoader,
    animeDetail: AnimeDetailInfo,
    onBackPressed: () -> Unit,
    onSeriesClick: (Int) -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    downloadAnimeEpisode: (String, String) -> Unit,
    onFavouriteIconCLick: (Boolean) -> Unit,
) {

    var showSeriesDialog by remember { mutableStateOf(false) }

    var showZoomableImage by remember { mutableStateOf(false) }

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
        imageLoader = imageLoader,
        onBackPressed = {
            coroutineScope.launch { modalSheetState.hide() }
        },
        modalSheetState = modalSheetState,
        onDownloadClick = { selectedList ->
            selectedList.forEach {
                downloadAnimeEpisode(
                    animeDetail.videos[it].videoUrl480,
                    animeDetail.videos[it].videoName
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

                item {
                    TopAnimeImage(
                        imageLoader = imageLoader,
                        animeItem = animeDetail,
                        onImageClick = { showZoomableImage = true })
                }

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
                        imageLoader = imageLoader,
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
                selected = animeDetail.isFavourite,
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

            if (showZoomableImage) {
                ZoomableImage(
                    image = animeDetail.image.original,
                    imageLoader = imageLoader,
                    onDismiss = {
                        showZoomableImage = false
                    },
                )
            }
        }
    }


}







