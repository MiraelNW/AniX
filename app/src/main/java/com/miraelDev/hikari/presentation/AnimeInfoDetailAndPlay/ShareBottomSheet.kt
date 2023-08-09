package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.hikari.R
import com.miraelDev.hikari.exntensions.pressClickEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShareBottomSheet(
        coroutineScope: CoroutineScope,
        modalSheetState: ModalBottomSheetState,
        onShareItemClick: (String) -> Unit,
        orientation :Int,
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
                                .fillMaxHeight(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 0.8f else 0.4f)
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