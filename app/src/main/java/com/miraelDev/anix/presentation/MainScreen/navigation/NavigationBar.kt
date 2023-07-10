package com.miraelDev.anix.presentation.MainScreen.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.miraelDev.anix.navigation.NavigationState

@Composable
fun BottomBar(navigationState: NavigationState) {

    val backStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

    val items =
        listOf(
            NavigationItem.Home,
            NavigationItem.Search,
            NavigationItem.Library
        )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 4.dp)
            .background(MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val selected = backStackEntry?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false
            AddItem(
                item = item,
                navigationState = navigationState,
                selected = selected
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    item: NavigationItem,
    navigationState: NavigationState,
    selected: Boolean
) {

    val background =
        if (selected) MaterialTheme.colors.primary.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) Color.White else MaterialTheme.colors.onBackground

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                if (!selected) {
                    navigationState.navigateTo(item.screen.route)
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = "icon",
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = stringResource(id = item.title),
                    color = Color.White
                )
            }
        }
    }
}