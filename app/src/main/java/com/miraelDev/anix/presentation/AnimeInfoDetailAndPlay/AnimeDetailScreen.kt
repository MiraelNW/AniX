package com.miraelDev.anix.presentation.AnimeInfoDetailAndPlay

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miraelDev.anix.domain.models.AnimeInfo
import com.miraelDev.anix.entensions.pressClickEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.transformation.blur.BlurTransformationPlugin
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeDetailScreen(
    animeId: Int,
    onBackPressed: () -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    onSeriesClick:(Int)->Unit
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
                    onSeriesClick = {
                        onSeriesClick(animeItem.id)
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






