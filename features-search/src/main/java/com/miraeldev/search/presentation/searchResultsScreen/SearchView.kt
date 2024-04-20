package com.miraeldev.search.presentation.searchResultsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraeldev.search.R

@Composable
fun AnimeSearchView(
    text: String,
    showFilter: Boolean,
    onFilterClicked: () -> Unit,
    onShowSearchHistory: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 18.dp, end = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_search),
            contentDescription = stringResource(R.string.search_icon),
            tint = MaterialTheme.colors.primary
        )
        Text(
            modifier = Modifier
                .clickable(onClick = { onShowSearchHistory(text) })
                .weight(1f)
                .padding(start = 16.dp),
            text = text
        )
        IconButton(
            onClick = { onShowSearchHistory("") }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close_icon),
                tint = Color.Gray
            )
        }
        Filter(
            visible = showFilter,
            onFilterClicked = onFilterClicked
        )
    }
}

@Composable
private fun Filter(
    visible: Boolean,
    onFilterClicked: () -> Unit
) {
    AnimatedVisibility(visible = visible) {
        IconButton(
            modifier = Modifier.size(36.dp),
            enabled = visible,
            onClick = onFilterClicked
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                contentDescription = stringResource(R.string.filter_icon),
                tint = MaterialTheme.colors.primary
            )
        }
    }

}
