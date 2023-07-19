package com.miraelDev.anix.presentation.AnimeInfoDetailAndPlay

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.miraelDev.anix.entensions.pressClickEffect

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeSeriesDialog(
    onDismiss: () -> Unit,
    onSeriesClick:()->Unit
) {

    val scrollState = rememberScrollState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(

        ),
        content = {

            val interactionSource = remember { MutableInteractionSource() }

            Card(
                modifier = Modifier
                    .fillMaxHeight(0.9f),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    Modifier.padding(12.dp),
                ) {
                    Text(
                        text = "Выберите серию",
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 32.sp,
                    )
                    FlowRow(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(24) {
                            Box(
                                modifier = Modifier
                                    .pressClickEffect()
                                    .fillMaxWidth()
                                    .padding(6.dp)
                                    .background(MaterialTheme.colors.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = null
                                        ) {
                                            Log.d("tag","click")
                                            onSeriesClick()
                                        }
                                        .padding(
                                            start = 8.dp,
                                            top = 8.dp,
                                            bottom = 4.dp,
                                            end = 2.dp
                                        )
                                        .background(MaterialTheme.colors.background),

                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.widthIn(max = 100.dp),
                                        text = "Серия $it",
                                        fontSize = 16.sp
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            modifier = Modifier.widthIn(min = 100.dp, max = 175.dp),
                                            text = "Какое то название серииgdfdfgdfgdfgdfg",
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontSize = 16.sp
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            modifier = Modifier.size(18.dp),
                                            imageVector = Icons.Filled.ArrowForward,
                                            contentDescription = "Watch series"
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }

        },
    )
}