package com.miraelDev.vauma.presentation.accountScreen.settings.notificationsScreen

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.vauma.R
import com.miraelDev.vauma.presentation.commonComposFunc.Toolbar

@Composable
fun NotificationScreen(onBackPressed: () -> Unit) {

    val onBackPressedAction = remember { { onBackPressed() } }

    BackHandler(onBack = onBackPressedAction)

    Column(modifier = Modifier.systemBarsPadding()) {
        Toolbar(onBackPressed = onBackPressedAction, text = R.string.notification_settings)
        NotificationsList()
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun NotificationsList() {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {

        var isSelectedTitles by remember {  mutableStateOf(false) }

        Column(modifier = Modifier.padding(4.dp)) {

            AnimatedContent(
                targetState = isSelectedTitles,
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = 150)) with
                            fadeOut(animationSpec = tween(durationMillis = 150))
                }, label = ""
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


                        Text(
                            text = stringResource(R.string.notification_of_released_titles),
                            fontSize = 18.sp
                        )
                        Switcher(isSelectedFun = { isSelectedTitles })
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
                    text = stringResource(R.string.notification_of_released_series),
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Switcher(isSelectedFun = { isSelectedEpisodes })
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
                    text = stringResource(R.string.notification_of_released_update),
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Switcher(isSelectedFun = { isSelectedUpdate })
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
            Text(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.all_titles))
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
                text = stringResource(R.string.titles_you_want_to_watch)
            )
        }
    }

}

@Composable
fun Switcher(
    isSelectedFun: () -> Boolean,
    size: Dp = 26.dp,
    padding: Dp = 4.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 500),
) {

    val isSelected = remember(isSelectedFun) { isSelectedFun() }

    val offset by animateDpAsState(
        targetValue = if (isSelected) size else 0.dp,
        animationSpec = animationSpec
    )

    Box(
        modifier = Modifier
            .width(size * 2)
            .height(size)
            .clip(shape = parentShape)
            .background(if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface)
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) size else 22.dp)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .align(Alignment.CenterStart)
                .background(Color.White)
        ) {}
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
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