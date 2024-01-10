package com.miraeldev.detailinfo.presentation

import android.view.MotionEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.miraeldev.detailinfo.R


@ExperimentalComposeUiApi
@Composable
fun UserRatingBar(
    modifier: Modifier = Modifier,
    rating: Int
) {
    var ratingState by remember {
        mutableIntStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_full_star),
                contentDescription = "star",
                modifier = modifier
                    .width(38.dp)
                    .height(38.dp)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                ratingState = i
                            }

                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) MaterialTheme.colors.primary
                else MaterialTheme.colors.primary.copy(0.3f)
            )
        }
    }
}