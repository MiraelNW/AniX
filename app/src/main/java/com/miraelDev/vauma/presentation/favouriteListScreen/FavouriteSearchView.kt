package com.miraelDev.vauma.presentation.animeListScreen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.miraelDev.vauma.R


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FavouriteSearchView(
    text: () -> String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseSearchView: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var enabled by rememberSaveable { mutableStateOf(true) }

    val source = remember { MutableInteractionSource() }
    val clearFocus = remember { { focusManager.clearFocus() } }
    val onSearchClickedAction: (String) -> Unit = remember { { onSearchClicked(it) } }
    val onTextChangeAction: (String) -> Unit = remember { { onTextChange(it) } }
    val onCloseSearchViewAction = remember { { onCloseSearchView() } }

    if (source.collectIsPressedAsState().value) {
        enabled = false
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text(),
            onValueChange = onTextChange,
            enabled = true,
            singleLine = true,
            interactionSource = source,
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),
                    color = MaterialTheme.colors.onBackground,
                    text = stringResource(id = R.string.search_anime)
                )
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colors.primary
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text().isNotEmpty()) {
                        onTextChangeAction(EMPTY_STRING)
                    } else {
                        onCloseSearchViewAction()
                        clearFocus()
                        keyboardController?.hide()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Gray
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClickedAction(text())
                    clearFocus()
                    keyboardController?.hide()
                }

            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF302F2F)
            )

        )
    }
}

private const val EMPTY_STRING = ""