package com.miraeldev.presentation.animation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miraeldev.presentation.ErrorRefreshItem
import com.miraeldev.theme.LocalOrientation

@Composable
fun WentWrongAnimation(
    modifier: Modifier = Modifier,
    res: Int,
    onClickRetry: () -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(res))
    val orientationLandscape = LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = modifier.padding(bottom = if (orientationLandscape) 0.dp else 36.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(if (orientationLandscape) 18.dp else 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size(if (orientationLandscape) 200.dp else 300.dp),
                composition = composition,
                iterations = 4
            )
            ErrorRefreshItem(onClickRetry = onClickRetry)
        }
    }

}
