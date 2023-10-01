package com.miraelDev.vauma.presentation.animeInfoDetailAndPlay

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.DecodeResult
import coil.decode.Decoder
import coil.decode.VideoFrameDecoder
import coil.fetch.Fetcher
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import coil.request.videoFrameOption
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miraelDev.vauma.R
import com.miraelDev.vauma.domain.models.AnimeDetailInfo
import com.miraelDev.vauma.exntensions.noRippleEffectClick
import com.miraelDev.vauma.presentation.mainScreen.LocalOrientation
import com.miraelDev.vauma.ui.theme.LightGreen
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DownloadBottomSheet(
    animeDetailInfo: AnimeDetailInfo,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    onDownloadClick: (List<Int>) -> Unit,
    onCloseDownloadSheet: () -> Unit,
    bottomSheetContent: @Composable () -> Unit
) {

    val orientation = LocalOrientation.current

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    val selectedList = remember { mutableStateListOf<Int>() }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        scrimColor = Color.Gray.copy(alpha = 0.5f),
        sheetContent = {

            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxHeight(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 0.8f else 0.5f)
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.download_episode),
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                    )
                }

                LazyRow(
                    modifier = Modifier.clip(RoundedCornerShape(24.dp)),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(count = animeDetailInfo.videoUrls.playerUrl.size, key = { it }) {
                        EpisodeItem(
                            url = animeDetailInfo.videoUrls.playerUrl[it],
                            videoName = animeDetailInfo.videoUrls.videoName[it],
                            selected = selectedList.contains(it),
                            onEpisodeItemClick = {
                                if (selectedList.toList().contains(it)) {
                                    selectedList.remove(it)
                                } else {
                                    selectedList.add(it)
                                }
                            }
                        )
                    }
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = onCloseDownloadSheet,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = LightGreen.copy(0.8f),
                            contentColor = MaterialTheme.colors.primary
                        )
                    ) {
                        Text(stringResource(R.string.close), fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onDownloadClick(selectedList)
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.download), fontSize = 18.sp)
                    }
                }
            }
        }
    ) {
        bottomSheetContent()
    }
}

@Composable
private fun EpisodeItem(
    url: String,
    videoName: String,
    selected: Boolean,
    onEpisodeItemClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .noRippleEffectClick { onEpisodeItemClick() }
            .padding(4.dp)
            .border(
                width = 3.dp,
                color = if (selected) MaterialTheme.colors.primary else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
    ) {
        GlideImage(
            modifier = Modifier
                .width(200.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            imageModel = { url },
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            },
            imageOptions = ImageOptions(
                contentDescription = stringResource(R.string.anime_episode_preview),
                contentScale = ContentScale.FillBounds,
            )
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp),
            text = videoName,
            color = Color.White
        )
    }
}