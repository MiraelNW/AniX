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
import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeDetailScreen(
    animeId: Int,
    onBackPressed: () -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    onSeriesClick:(Int,Int)->Unit
) {
    BackHandler { onBackPressed() }

    val list = mutableListOf<AnimeInfo>().apply {
        repeat(20) {
            add(AnimeInfo(it))
        }
    }

    val animeItem = AnimeInfo(animeId)

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
                        onSeriesClick(animeItem.id, videoId)
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .navigationBarsPadding(),
            ) {

                item { TopAnimeImage(animeItem = animeItem) }

                item {
                    AnimeNameAndShareButton(
                        animeItem = animeItem,
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

                item { RatingAndCategoriesRow(animeItem = animeItem) }

                item { GenreRow(animeItem = animeItem) }

                item { ExpandableDescription(text = animeItem.description) }

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






