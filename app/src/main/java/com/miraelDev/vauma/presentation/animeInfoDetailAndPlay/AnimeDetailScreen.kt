package com.miraelDev.vauma.presentation.animeInfoDetailAndPlay

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.miraelDev.vauma.R
import com.miraelDev.vauma.domain.models.AnimeDetailInfo
import com.miraelDev.vauma.presentation.shimmerList.ShimmerListAnimeDetail
import kotlinx.coroutines.launch


@Composable
fun AnimeDetailScreen(
    animeId: Int,
    onBackPressed: () -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    onSeriesClick: () -> Unit
) {
    BackHandler { onBackPressed() }

    val viewModel = hiltViewModel<AnimeDetailViewModel>()

    val screenState by viewModel.animeDetail.collectAsStateWithLifecycle(AnimeDetailScreenState.Initial)

    when (val results = screenState) {

        is AnimeDetailScreenState.SearchResult -> {
            DetailScreen(
                animeDetail = results.result.first(),
                onBackPressed = onBackPressed,
                onAnimeItemClick = onAnimeItemClick,
                onSeriesClick = { videoId ->
                    viewModel.loadVideoId(videoId)
                    onSeriesClick()
                },
                downloadAnimeEpisode = viewModel::downloadEpisode,
                onFavouriteIconCLick = { isSelected ->
                    viewModel.selectAnimeItem(isSelected, results.result.first())
                }

            )
        }

        is AnimeDetailScreenState.SearchFailure -> {
        }

        is AnimeDetailScreenState.Loading -> {
            ShimmerListAnimeDetail()

            viewModel.loadAnimeDetail(animeId)

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

    DownloadBottomSheet(
        animeDetailInfo = animeDetail,
        coroutineScope = coroutineScope,
        modalSheetState = modalSheetState,
        onDownloadClick = { videoUrl, videoName ->
            downloadAnimeEpisode(videoUrl, videoName)
            coroutineScope.launch {
                modalSheetState.hide()
            }
        },
        onCloseDownloadSheet = {
            coroutineScope.launch {
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
                                "Привет! Смотри какое интересное аниме нашел: ${animeDetail.nameRu}, рекомендую глянуть его в этом приложение"
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
                                modalSheetState.show()
                            }
                        }
                    )
                }

                item { RatingAndCategoriesRow(animeItem = animeDetail) }

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







