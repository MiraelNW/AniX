package com.miraelDev.vauma.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.common.collect.ImmutableList
import com.miraeldev.exntensions.noRippleEffectClick
import com.miraelDev.vauma.navigation.NavigationState

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navigationState: NavigationState
) {

    val backStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

    val items =
        ImmutableList.of(
            NavigationItem.Home,
            NavigationItem.Search,
            NavigationItem.Favourite,
            NavigationItem.Account,
        )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .navigationBarsPadding()
                .align(Alignment.Center),
            elevation = 6.dp,
            shape = RoundedCornerShape(24.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
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
    }


}

@Composable
private fun RowScope.AddItem(
    item: NavigationItem,
    navigationState: NavigationState,
    selected: Boolean
) {

    val background =
        if (selected) MaterialTheme.colors.primary else Color.Transparent

    val contentColor =
        if (selected) Color.White else MaterialTheme.colors.onBackground

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .noRippleEffectClick(
                onClick = {
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
                imageVector = ImageVector.vectorResource(item.icon),
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