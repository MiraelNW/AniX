package com.miraelDev.hikari.presentation.AnimeListScreen.SettingsScreen.NotificationsScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.hikari.R

@Composable
fun NotificationScreen(onBackPressed: () -> Unit) {
    BackHandler() { onBackPressed() }

    Column(modifier = Modifier.systemBarsPadding()) {
        Toolbar(onBackPressed = onBackPressed)
        NotificationsList()
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun NotificationsList() {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {

        var isSelectedTitles by remember { mutableStateOf(false) }

        Column(modifier = Modifier.padding(4.dp)) {

            AnimatedContent(
                targetState = isSelectedTitles,
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = 150)) with
                            fadeOut(animationSpec = tween(durationMillis = 150))
                }
            ) { isSelected ->
                Column() {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { isSelectedTitles = !isSelectedTitles }
                            .padding(top = 8.dp, end = 4.dp, start = 4.dp, bottom = 4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Text(text = "Уведомление о вышедших тайтлах", fontSize = 18.sp)
                        Switcher(isSelected = isSelectedTitles)
                    }

                    if (isSelected) {
                        CheckBoxesWithText()
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            var isSelectedEpisodes by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { isSelectedEpisodes = !isSelectedEpisodes }
                    .padding(top = 8.dp, end = 4.dp, start = 4.dp, bottom = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = "Уведомление о вышедших сериях понравившихся аниме",
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Switcher(isSelected = isSelectedEpisodes)
            }

            Spacer(modifier = Modifier.height(8.dp))

            var isSelectedUpdate by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { isSelectedUpdate = !isSelectedUpdate }
                    .padding(top = 8.dp, end = 4.dp, start = 4.dp, bottom = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = "Уведомление о вышедшем обновлении",
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Switcher(isSelected = isSelectedUpdate)
            }
        }

    }

}

@Composable
private fun CheckBoxesWithText() {
    Column() {

        var allTitlesIsChecked by remember { mutableStateOf(true) }

        var titlesInWishListIsChecked by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    if (!allTitlesIsChecked) {
                        allTitlesIsChecked = !allTitlesIsChecked
                        titlesInWishListIsChecked = false
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = allTitlesIsChecked,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary,
                    uncheckedColor = MaterialTheme.colors.primary,
                    disabledColor = MaterialTheme.colors.primary,
                    disabledIndeterminateColor = MaterialTheme.colors.primary
                ),
                enabled = !allTitlesIsChecked,
                onCheckedChange = {
                    allTitlesIsChecked = !allTitlesIsChecked
                    titlesInWishListIsChecked = false
                })
            Text(modifier = Modifier.fillMaxWidth(), text = "Все тайтлы")
        }
        Row(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    if (!titlesInWishListIsChecked) {
                        titlesInWishListIsChecked = !titlesInWishListIsChecked
                        allTitlesIsChecked = false
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = titlesInWishListIsChecked,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary,
                    uncheckedColor = MaterialTheme.colors.primary,
                    disabledColor = MaterialTheme.colors.primary,
                    disabledIndeterminateColor = MaterialTheme.colors.primary
                ),
                enabled = !titlesInWishListIsChecked,
                onCheckedChange = {
                    titlesInWishListIsChecked = !titlesInWishListIsChecked
                    allTitlesIsChecked = false
                })
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Тайтлы, которые вы хотели бы посмотреть"
            )
        }
    }

}

@Composable
private fun Toolbar(
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .width(8.dp)
                )
                Text(
                    text = "Настройки уведомлений",
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif,
                )
            }

        }
    }
}


@Composable
fun Switcher(
    isSelected: Boolean,
    size: Dp = 30.dp,
    padding: Dp = 4.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
) {
    val offset by animateDpAsState(
        targetValue = if (isSelected) size else 0.dp,
        animationSpec = animationSpec
    )

    Box(
        modifier = Modifier
            .width(size * 2)
            .height(size)
            .clip(shape = parentShape)
            .background(if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.background)
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) size else 24.dp)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .align(Alignment.CenterStart)
                .background(
                    if (isSelected) MaterialTheme.colors.background
                    else MaterialTheme.colors.primary.copy(0.6f)
                )
        ) {}
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = if (isSelected) MaterialTheme.colors.primary
                        else MaterialTheme.colors.primary.copy(0.6f)
                    ),
                    shape = parentShape
                )
        ) {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {}
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {}
        }
    }
}