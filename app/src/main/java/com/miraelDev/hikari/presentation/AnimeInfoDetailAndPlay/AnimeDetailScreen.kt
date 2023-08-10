package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.presentation.SearchAimeScreen.SearchAnimeScreenState
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerList
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerListAnimeDetail
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

    LaunchedEffect(Unit) {
        viewModel.loadAnimeDetailUseCase(animeId)
    }

    val screenState by viewModel.animeDetail.collectAsState(AnimeDetailScreenState.Initial)

    when (val results = screenState) {

        is AnimeDetailScreenState.SearchResult -> {
            DetailScreen(
                    animeDetail = results.result.first(),
                    onBackPressed = onBackPressed,
                    onAnimeItemClick = onAnimeItemClick,
                    onSeriesClick = { videoId ->
                        viewModel.loadVideoIdUseCase(videoId)
                        onSeriesClick()
                    }
            )
        }

        is AnimeDetailScreenState.SearchFailure -> {
            Log.d("tag", "failure")
        }

        is AnimeDetailScreenState.Loading -> {
            Log.d("tag", "load")
            ShimmerListAnimeDetail()
        }

        is AnimeDetailScreenState.Initial -> {}
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DetailScreen(
        animeDetail: AnimeInfo,
        onBackPressed: () -> Unit,
        onSeriesClick: (Int) -> Unit,
        onAnimeItemClick:(Int)->Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var showSeriesDialog by remember { mutableStateOf(false) }

    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    val list = mutableListOf<AnimeInfo>().apply {
        repeat(20) {
            add(AnimeInfo(it))
        }
    }

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
                .collect { orientation = it }
    }

    ShareBottomSheet(
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState,
            orientation = orientation,
            onShareItemClick = { sourceName ->

            }
    ) {
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
                                coroutineScope.launch {
                                    modalSheetState.show()
                                }
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
        }
    }
}






