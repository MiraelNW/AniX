package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeDetailScreen(
    animeId: Int,
    onBackPressed: () -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    onSeriesClick:()->Unit
) {
    BackHandler { onBackPressed() }

    val viewModel = hiltViewModel<AnimeDetailViewModel>()

    viewModel.loadAnimeDetailUseCase(animeId)

    val animeDetail by viewModel.animeDetail.collectAsState()

    val list = mutableListOf<AnimeInfo>().apply {
        repeat(20) {
            add(AnimeInfo(it))
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var showSeriesDialog by remember { mutableStateOf(false) }

    ShareBottomSheet(
        coroutineScope = coroutineScope,
        modalSheetState = modalSheetState,
        onShareItemClick = { sourceName ->

        }
    ) {
        Box{

            if (showSeriesDialog) {
                AnimeSeriesDialog(
                    onDismiss = {
                        showSeriesDialog = false
                    },
                    onSeriesClick = {videoId ->
                        viewModel.loadVideoIdUseCase(videoId)
                        onSeriesClick()
                    }
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






