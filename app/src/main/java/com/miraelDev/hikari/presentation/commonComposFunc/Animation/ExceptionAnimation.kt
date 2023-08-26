package com.miraelDev.hikari.presentation.commonComposFunc.Animation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miraelDev.hikari.presentation.commonComposFunc.ErrorRefreshItem

@Composable
fun WentWrongAnimation(
    res: Int,
    onClickRetry: () -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(res))
    Box(
        modifier = Modifier
            .height(500.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size(300.dp),
                composition = composition,
                iterations = 4
            )
            ErrorRefreshItem(onClickRetry = onClickRetry)
        }
    }

}
