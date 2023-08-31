package com.miraelDev.hikari.presentation.animeListScreen.animeList

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.miraelDev.hikari.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SettingsAndThemeButtons(
    onSettingsClick: () -> Unit,
    onThemeButtonClick: () -> Unit,
    darkTheme: Boolean
) {
    Row {

        AnimatedContent(
            targetState = darkTheme,
            transitionSpec = {
                fadeIn(animationSpec = tween(150)) +
                        scaleIn(
                            initialScale = 0.4f,
                            animationSpec = tween(300)
                        ) with
                        fadeOut(animationSpec = tween(150))
            }
        ) { darkTheme ->

            val iconId = if (darkTheme) {
                R.drawable.ic_sun
            } else {
                R.drawable.ic_moon
            }

            IconButton(
                modifier = Modifier
                    .size(28.dp)
                    .padding(2.dp),
                onClick = { onThemeButtonClick() }
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Change theme"
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
                .width(12.dp)
        )

        IconButton(
            onClick = { onSettingsClick() },
            Modifier
                .size(28.dp)
                .padding(2.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Settings",
                modifier = Modifier.fillMaxSize(),
            )
        }

    }

}