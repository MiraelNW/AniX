package com.miraeldev.account.presentation.screens.account.accountComponent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.miraeldev.account.R
import com.miraeldev.account.domain.UserModel
import com.miraeldev.account.presentation.screens.notificationsScreen.Switcher
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.designsystem.Toolbar
import com.miraeldev.models.anime.Settings
import com.miraeldev.theme.LocalTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AccountScreen(component: AccountComponent, imageLoader: VaumaImageLoader) {

    val model by component.model.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Toolbar(
            text = stringResource(id = R.string.profile),
            color = MaterialTheme.colors.onBackground
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileNameAndImage(model.userModel, imageLoader)
            AllSettings(
                onSettingItemClick = component::onSettingItemClick,
                onDarkThemeClick = component::onDarkThemeClick,
                onLogOutClick = component::logOut
            )
        }
    }
}

@Composable
private fun ProfileNameAndImage(userInfo: UserModel, imageLoader: VaumaImageLoader) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape),
            model = imageLoader.load { data(userInfo.image) },
            contentDescription = "profile image",
            contentScale = ContentScale.Crop,
//            failure = {
//                Image(
//                    imageVector = ImageVector.vectorResource(R.drawable.ic_placeholder),
//                    contentDescription = "place holder"
//                )
//            }
        )
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = userInfo.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground,
                fontSize = 24.sp
            )
            Text(
                text = userInfo.email.ifEmpty { "no email" },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground.copy(0.6f),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun AllSettings(
    onSettingItemClick: (Settings) -> Unit,
    onDarkThemeClick: (Boolean) -> Unit,
    onLogOutClick: () -> Unit,
) {
    val settings = persistentListOf(
        Settings.EDIT_PROFILE,
        Settings.NOTIFICATIONS,
        Settings.DOWNLOADS
    )

    val settingsItems = persistentListOf(
        stringResource(R.string.edit_profile) to R.drawable.ic_edit_profile,
        stringResource(R.string.notification) to R.drawable.ic_notification,
        stringResource(R.string.download_video) to R.drawable.ic_download_videos,
    )

    Column(
        modifier = Modifier.padding(bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        settings.forEachIndexed { index, setting ->
            SettingItem(
                setting = setting,
                text = settingsItems[index].first,
                icon = settingsItems[index].second,
                onSettingItemClick = onSettingItemClick
            )
        }

        DarkTheme(onDarkThemeClick = onDarkThemeClick)

        PrivacyPolicyItem(onPrivacyPolicyItemClick = onSettingItemClick)

        LogOut(onLogOutClick = onLogOutClick)
    }
}

@Composable
private fun SettingItem(
    setting: Settings,
    text: String,
    icon: Int,
    onSettingItemClick: (Settings) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onSettingItemClick(setting) }
            .padding(8.dp)
            .border(0.dp, MaterialTheme.colors.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = text,
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_forward),
            contentDescription = stringResource(R.string.show),
            tint = MaterialTheme.colors.onBackground
        )

    }
}

@Composable
private fun PrivacyPolicyItem(
    onPrivacyPolicyItemClick: (Settings) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onPrivacyPolicyItemClick(Settings.PRIVACY_POLICY) }
            .padding(8.dp)
            .border(0.dp, MaterialTheme.colors.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_privacy_policy),
                contentDescription = stringResource(id = R.string.privacy_policy),
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(id = R.string.privacy_policy),
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_forward),
            contentDescription = stringResource(R.string.show),
            tint = MaterialTheme.colors.onBackground
        )

    }
}

@Composable
private fun DarkTheme(
    onDarkThemeClick: (Boolean) -> Unit
) {

    val isDarkTheme = LocalTheme.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onDarkThemeClick(!isDarkTheme) }
            .padding(8.dp)
            .border(0.dp, MaterialTheme.colors.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = isDarkTheme,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150)) +
                            scaleIn(
                                initialScale = 0.4f,
                                animationSpec = tween(300)
                            ) togetherWith
                            fadeOut(animationSpec = tween(150))
                }, label = ""
            ) { darkTheme ->

                val iconId = if (darkTheme) {
                    R.drawable.ic_sun
                } else {
                    R.drawable.ic_moon
                }
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(iconId),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Change theme"
                )

            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.dark_theme),
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp
            )
        }

        Switcher(isSelectedFun = { isDarkTheme })

    }
}

@Composable
private fun LogOut(
    onLogOutClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onLogOutClick() }
            .padding(8.dp)
            .border(0.dp, MaterialTheme.colors.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_log_out),
                contentDescription = stringResource(R.string.logOut),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.logOut),
                color = Color.Red,
                fontSize = 18.sp
            )
        }
    }
}



