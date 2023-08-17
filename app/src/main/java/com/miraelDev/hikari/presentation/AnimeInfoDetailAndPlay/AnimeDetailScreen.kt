package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import android.content.Intent
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerListAnimeDetail


@Composable
fun AnimeDetailScreen(
        animeId: Int,
        onBackPressed: () -> Unit,
        onAnimeItemClick: (Int) -> Unit,
        onSeriesClick: () -> Unit
) {
    BackHandler { onBackPressed() }

    val viewModel = hiltViewModel<AnimeDetailViewModel>()

    val screenState by viewModel.animeDetail.collectAsState(AnimeDetailScreenState.Initial)

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
                    onFavouriteIconCLick = { isSelected ->
                        viewModel.selectAnimeItem(isSelected, results.result.first())
                    }

            )
        }

        is AnimeDetailScreenState.SearchFailure -> {
            Log.d("tag", "failure")
        }

        is AnimeDetailScreenState.Loading -> {
            ShimmerListAnimeDetail()

            viewModel.loadAnimeDetail(animeId)

        }

        is AnimeDetailScreenState.Initial -> {}
    }

}

@Composable
private fun DetailScreen(
        animeDetail: AnimeInfo,
        onBackPressed: () -> Unit,
        onSeriesClick: (Int) -> Unit,
        onAnimeItemClick: (Int) -> Unit,
        onFavouriteIconCLick: (Boolean) -> Unit,
) {

    var showSeriesDialog by remember { mutableStateOf(false) }

    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    val context = LocalContext.current

    val list = mutableListOf<AnimeInfo>().apply {
        repeat(20) {
            add(AnimeInfo(it))
        }
    }

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
                .collect { orientation = it }
    }

    Box {

        if (showSeriesDialog) {
            AnimeSeriesDialog(
                    onDismiss = {
                        showSeriesDialog = false
                    },
                    onSeriesClick = onSeriesClick
            )
        }

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
                                    "Привет! Смотри какое интересное аниме нашел, рекомендую глянуть его в этом приложение"
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
                PlayButton() {
                    showSeriesDialog = true
                }
            }

            item { RatingAndCategoriesRow(animeItem = animeDetail) }

            item { GenreRow(animeItem = animeDetail) }

            item { ExpandableDescription(text = animeDetail.description) }

            item {
                Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Смотрите также",
                        fontSize = 24.sp
                )
            }

            item { OtherAnimes(animeList = list, onAnimeItemClick = onAnimeItemClick) }
        }
        BackIcon(onBackPressed = onBackPressed)

        FavouriteIcon(
                modifier = Modifier
                        .align(Alignment.TopEnd)
                        .statusBarsPadding()
                        .padding(end = 8.dp, top = 8.dp),
                onFavouriteIconClick = onFavouriteIconCLick
        )
    }
}







