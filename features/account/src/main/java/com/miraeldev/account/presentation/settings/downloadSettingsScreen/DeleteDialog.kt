package com.miraeldev.account.presentation.settings.downloadSettingsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.miraeldev.account.R
import com.miraeldev.theme.LightGreen


private const val DELETE_VIDEO = 1

@Composable
fun DeleteDialog(
    index: Int,
    onDismiss: () -> Unit,
    onDeleteClick: (Int) -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(

        ),
        content = {


            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    Modifier.padding(20.dp),
                ) {
                    Text(
                        text =
                        if (index == DELETE_VIDEO)
                            "Вы уверены, что хотите удалить все  скаченные видео?"
                        else
                            "Вы уверены, что хотите очистить кеш?",
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            )
                            .align(Alignment.CenterHorizontally),
                        fontSize = 22.sp,
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            onClick = onDismiss,
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = LightGreen.copy(0.8f),
                                contentColor = MaterialTheme.colors.primary
                            )
                        ) {
                            Text(stringResource(R.string.cancel), fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onDeleteClick(index)
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(R.string.delete), fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    )
}