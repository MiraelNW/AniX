package com.miraelDev.vauma.presentation.animeListScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.vauma.R


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimeSearchView(
    text: TextFieldValue,
    showFilter: Boolean,
    isSearchHistoryItemClick: Boolean,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    open: Boolean,
    onFilterClicked: (SoftwareKeyboardController?) -> Unit,
    clickOnSearchView: () -> Unit,
    onCloseSearchView: () -> Unit,
    onClearText: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var expanded by rememberSaveable { mutableStateOf(false) }
    var isSearchKeyClick by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = open) {
        expanded = open
    }

    val animatedHeightState by animateDpAsState(if (expanded) 60.dp else 50.dp)
    val animatedWidthState by animateFloatAsState(if (expanded) 0.85f else 0.45f)
    val animatedTextSizeState by animateIntAsState(if (expanded) 16 else 14)

    val source = remember { MutableInteractionSource() }
    var clicked by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isSearchHistoryItemClick, key2 = isSearchKeyClick) {
        if (isSearchHistoryItemClick || isSearchKeyClick) {
            clicked = false
            isSearchKeyClick = false
            onSearchClicked(text.text)
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    if (source.collectIsPressedAsState().value) {
        expanded = true
        clicked = true
    }

    if (clicked) {
        clickOnSearchView()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(animatedWidthState)
                .height(animatedHeightState)
                .focusRequester(focusRequester),
            value = text,
            onValueChange = { onTextChange(it.text) },
            enabled = true,
            singleLine = true,
            interactionSource = source,
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),

                    fontSize = animatedTextSizeState.sp,
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
                if (expanded) {
                    IconButton(onClick = {
                        if (text.text.isNotEmpty()) {
                            onTextChange("")
                            onClearText()
                        } else {
                            expanded = !expanded
                            clicked = false
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
                    isSearchKeyClick = true
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF302F2F)
            )

        )
        Filter(showFilter, keyboardController, onFilterClicked = onFilterClicked)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Filter(
    visible: Boolean,
    keyboardController: SoftwareKeyboardController?,
    onFilterClicked: (SoftwareKeyboardController?) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        IconButton(
            modifier = Modifier.size(36.dp),
            enabled = visible,
            onClick = { onFilterClicked(keyboardController) }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                contentDescription = stringResource(R.string.filter_icon),
                tint = MaterialTheme.colors.primary
            )
        }
    }

}