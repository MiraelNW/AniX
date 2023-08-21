package com.miraelDev.hikari.presentation.AnimeListScreen.SettingsScreen.ColorPalette

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.common.collect.ImmutableList
import com.guru.composecookbook.theme.HikariTheme
import com.miraelDev.hikari.R
import com.miraelDev.hikari.exntensions.noRippleEffectClick
import com.miraelDev.hikari.presentation.MainScreen.LocalColor
import com.miraelDev.hikari.ui.theme.Blue
import com.miraelDev.hikari.ui.theme.ColorPallet
import com.miraelDev.hikari.ui.theme.Green

@Composable
fun ColorPaletteScreen(
        onColorThemeChoose: (Int) -> Unit,
        onBackPressed: () -> Unit
) {

    BackHandler(onBack = onBackPressed)


    Column(
            modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Toolbar(onBackPressed = onBackPressed)

        Column(
                modifier = Modifier
                        .systemBarsPadding()
                        .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ThemePreview()

            ColorRow(onColorThemeChoose = onColorThemeChoose)
        }
    }

}

@Composable
private fun ColorRow(
        onColorThemeChoose: (Int) -> Unit
) {

    var selectedIndex by remember { mutableStateOf(0) }

    val colorList = ImmutableList.of(
            ColorPallet.GREEN,
            ColorPallet.ORANGE,
            ColorPallet.PURPLE,
    )

    colorList.forEachIndexed { index, color ->
        if (LocalColor.current == color) {
            selectedIndex = index
        }
    }

    val scrollState = rememberScrollState()

    Row(
            modifier = Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxWidth()
                    .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        colorList.forEachIndexed { index, color ->
            ThemeItem(
                    colorPallet = color,
                    selected = selectedIndex == index,
                    onItemClick = {
                        selectedIndex = index
                        onColorThemeChoose(selectedIndex)
                    }
            )
        }
    }
}

@Composable
private fun ThemePreview() {
    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
                modifier = Modifier
                        .fillMaxWidth(0.48f)
                        .height(400.dp),
                elevation = 4.dp
        ) {
            HikariTheme(darkTheme = false, colorPallet = LocalColor.current) {
                AnimeList()
            }
        }

        Card(
                modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .height(400.dp),
                elevation = 4.dp
        ) {
            HikariTheme(darkTheme = true, colorPallet = LocalColor.current) {
                AnimeList()
            }

        }
    }
}


@Composable
private fun ThemeItem(
        selected: Boolean,
        colorPallet: ColorPallet,
        onItemClick: () -> Unit
) {
    val borderWidth by animateDpAsState(targetValue = 2.dp, animationSpec = spring())

    val color = when(colorPallet){

        ColorPallet.GREEN ->{
            Color.Green
        }

        ColorPallet.ORANGE ->{
            Color.Red
        }

        ColorPallet.PURPLE ->{
            Blue
        }

    }

    Card(
            modifier = Modifier
                    .padding(4.dp)
                    .size(100.dp, 150.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(24.dp),
            border =
            if (selected) BorderStroke(borderWidth, color = color)
            else BorderStroke(0.dp, color = Color.Transparent),
    ) {
        Column(
                modifier = Modifier
                        .background(color.copy(0.05f))
                        .noRippleEffectClick(
                                interactionSource = MutableInteractionSource(),
                                onClick = onItemClick
                        ),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Box(modifier = Modifier
                    .size(50.dp, 30.dp)
                    .padding(top = 8.dp, bottom = 4.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(color)
            )

            Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_brand_icon),
                    contentDescription = "app Icon",
                    tint = color
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
                        text = "Настройки цветовой палитры",
                        fontSize = 22.sp,
                        color = MaterialTheme.colors.onBackground,
                        fontFamily = FontFamily.SansSerif,
                )
            }

        }
    }
}