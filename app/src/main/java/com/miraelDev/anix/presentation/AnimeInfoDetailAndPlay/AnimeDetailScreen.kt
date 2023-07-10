package com.miraelDev.anix.presentation.AnimeInfoDetailAndPlay

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miraelDev.anix.R
import com.miraelDev.anix.domain.models.AnimeInfo
import com.miraelDev.anix.entensions.pressClickEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.transformation.blur.BlurTransformationPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeDetailScreen(
    animeId: Int,
    onBackPressed: () -> Unit,
    onAnimeItemClick: (Int) -> Unit
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

    ShareBottomSheet(
        coroutineScope = coroutineScope,
        modalSheetState = modalSheetState,
        onShareItemClick = { sourceName ->

        }
    ) {
        Box {

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

                item { PlayButton() }

                item { RatingAndCategoriesRow(animeItem = animeItem) }

                item { GenreRow(animeItem = animeItem) }

                item { ExpandableText(text = animeItem.description) }

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


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ShareBottomSheet(
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    onShareItemClick: (String) -> Unit,
    bottomSheetContent: @Composable () -> Unit
) {


    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        scrimColor = Color.Gray.copy(alpha = 0.5f),
        sheetContent = {

            val nameAndLogoList = listOf(
                "Vk" to R.drawable.ic_vk,
                "Telegram" to R.drawable.ic_telegram,
                "WhatsApp" to R.drawable.ic_whatsapp,
                "Twitter" to R.drawable.ic_twitter,
                "Messages" to R.drawable.ic_messages,
                "Instagram" to R.drawable.ic_instagram,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Text(text = "Поделиться в", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Divider(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    nameAndLogoList.takeLast(3).forEach { nameAndLogo ->
                        ShareItem(nameAndLogo.first, nameAndLogo.second, onShareItemClick)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    nameAndLogoList.take(3).forEach { nameAndLogo ->
                        ShareItem(nameAndLogo.first, nameAndLogo.second, onShareItemClick)
                    }
                }
            }
        }
    ) {
        bottomSheetContent()
    }
}

@Composable
private fun ShareItem(name: String, logo: Int, onShareItemClick: (String) -> Unit) {

    Column(
        modifier = Modifier
            .pressClickEffect()
            .clickable { onShareItemClick(name) }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            painter = painterResource(id = logo),
            contentDescription = "whats app icon",
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name)
    }
}


@Composable
private fun OtherAnimes(
    animeList: List<AnimeInfo>,
    onAnimeItemClick: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(8.dp),
        contentPadding = PaddingValues(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = animeList, key = { it.id }) {
            AnimeCard(animeItem = it, onAnimeItemClick = onAnimeItemClick)
        }
    }
}

@Composable
private fun BackIcon(onBackPressed: () -> Unit) {
    Box() {
        IconButton(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 8.dp, top = 8.dp)
                .size(35.dp)
                .align(Alignment.TopStart),
            onClick = onBackPressed
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .border(1.dp, Color.Gray.copy(0.4f))
                    .background(Color.White.copy(0.9f)),
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Arrow back",
                    tint = Color.Gray
                )
            }

        }
    }

}

@Composable
private fun RatingAndCategoriesRow(animeItem: AnimeInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "rating star",
                tint = MaterialTheme.colors.primary
            )
            Text(text = animeItem.score.toString(), color = MaterialTheme.colors.primary)
        }

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = animeItem.kind,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "${animeItem.episodes} episodes",
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = animeItem.status,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary
                )
            }
        }

    }
}

@Composable
private fun GenreRow(animeItem: AnimeInfo) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "Жанры: ", fontSize = 18.sp)
        animeItem.genres.forEachIndexed { index, genre ->
            Text(
                text = if (index != animeItem.genres.size) " ${genre.lowercase()},"
                else " ${genre.lowercase()}",
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = 3,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .padding(8.dp)
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(style = showLessStyle.copy(MaterialTheme.colors.primary)) {
                            append(
                                showLessText
                            )
                        }
                    } else {
                        val adjustText =
                            text.substring(startIndex = 0, endIndex = lastCharIndex)
                                .dropLast(showMoreText.length)
                                .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle.copy(MaterialTheme.colors.primary)) {
                            append(
                                showMoreText
                            )
                        }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign
        )
    }

}

@Composable
private fun PlayButton() {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .pressClickEffect()
            .padding(top = 30.dp, start = 16.dp, end = 16.dp),
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            Icon(
                modifier = Modifier.padding(2.dp),
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "play icon",
                tint = MaterialTheme.colors.primary
            )
        }

        Text(
            modifier = Modifier.padding(4.dp),
            text = "Play",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}


@Composable
private fun AnimeNameAndShareButton(
    animeItem: AnimeInfo,
    onShareButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            Text(
                text = animeItem.nameRu,
                fontSize = 24.sp,
            )
            Text(
                text = animeItem.nameEn,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onBackground.copy(0.8f)
            )
        }

        IconButton(onClick = onShareButtonClick) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "share button",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
private fun TopAnimeImage(
    animeItem: AnimeInfo,
) {
    Card(
        elevation = 4.dp
    ) {
        Box {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                imageModel = { animeItem.image },
                imageOptions = ImageOptions(
                    contentDescription = "anime image preview",
                    contentScale = ContentScale.FillBounds,
                ),
                component = rememberImageComponent {
                    +BlurTransformationPlugin(radius = 30)
                }
            )
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .size(150.dp, 250.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp
            ) {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = { animeItem.image },
                    imageOptions = ImageOptions(
                        contentDescription = "anime image preview",
                    )
                )
            }

        }


    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimeCard(animeItem: AnimeInfo, onAnimeItemClick: (Int) -> Unit) {

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(700)
        )
    }

    val animatedModifier = Modifier.alpha(animatedProgress.value)
    Column(
        modifier = animatedModifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            onClick = { onAnimeItemClick(animeItem.id) },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .size(width = 175.dp, height = 275.dp)
                .fillMaxWidth()
                .pressClickEffect(),
            elevation = 4.dp
        ) {

            AsyncImage(
                modifier = Modifier
                    .height(300.dp)
                    .width(230.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = animeItem.image,
                contentDescription = animeItem.nameEn,
                contentScale = ContentScale.FillBounds,

                )

        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            text = animeItem.nameRu,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}