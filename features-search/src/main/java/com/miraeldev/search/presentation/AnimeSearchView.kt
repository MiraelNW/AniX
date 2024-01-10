package com.miraeldev.search.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimeSearchView(
    text: () -> String,
    showFilter: Boolean,
    isSearchHistoryItemClick: Boolean,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onFilterClicked: () -> Unit,
    clickOnSearchView: () -> Unit,
    onCloseSearchView: () -> Unit,
    onClearText: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val source = remember { MutableInteractionSource() }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val clickOnSearchViewAction = remember { { clickOnSearchView() } }
    val clearFocusAction = remember { { focusManager.clearFocus() } }
    val hideKeyboardAction = remember { { keyboardController?.hide() } }
    val onSearchClickedAction: (String) -> Unit = remember { { onSearchClicked(it) } }

    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = showFilter) {
        expanded = showFilter
    }

    LaunchedEffect(key1 = isSearchHistoryItemClick) {
        snapshotFlow { isSearchHistoryItemClick }
            .collect { clicked ->
                if (clicked) {
                    onSearchClickedAction(text())
                    clearFocusAction()
                    hideKeyboardAction()
                }
            }
    }

    if (source.collectIsPressedAsState().value) {
        clickOnSearchViewAction()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(if (expanded) 0.85f else 0.45f)
                .height(if (expanded) 60.dp else 50.dp)
                .focusRequester(focusRequester),
            value = text(),
            onValueChange = onTextChange,
            enabled = true,
            singleLine = true,
            interactionSource = source,
            placeholder = {
                Text(
                    modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                    fontSize = 15.sp,
                    color = MaterialTheme.colors.onBackground,
                    text = stringResource(R.string.search_anime)
                )
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search_icon),
                    tint = MaterialTheme.colors.primary
                )
            },
            trailingIcon = {
                if (showFilter) {
                    IconButton(onClick = {
                        if (text().isNotEmpty()) {
                            onTextChange("")
                            onClearText()
                        } else {
                            onCloseSearchView()
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.close_icon),
                            tint = Color.Gray
                        )

                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClickedAction(text())
                    clearFocusAction()
                    hideKeyboardAction()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF302F2F)
            )

        )
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