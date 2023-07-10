package com.miraelDev.anix.presentation.AnimeListScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.anix.R


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimeSearchView(
    text: String,
    showFilter: Boolean,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onFilterClicked: (SoftwareKeyboardController?) -> Unit,
    clickOnSearchView: @Composable () -> Unit,
    onCloseSearchView: () -> Unit,
    onClearText: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var enabled by rememberSaveable { mutableStateOf(true) }

    val animatedHeightState by animateDpAsState(if (enabled) 50.dp else 60.dp)
    val animatedWidthState by animateFloatAsState(if (enabled) 0.45f else 0.85f)
    val animatedTextSizeState by animateIntAsState(if (enabled) 14 else 16)

    val source = remember { MutableInteractionSource() }
    var clicked by remember { mutableStateOf(false) }

    if (source.collectIsPressedAsState().value) {
        enabled = false
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
            onValueChange = {
                onTextChange(it)
            },
            enabled = true,
            singleLine = true,
            interactionSource = source,
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),

                    fontSize = animatedTextSizeState.sp,
                    color = MaterialTheme.colors.onBackground,
                    text = "Search anime..."
                )
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colors.primary
                )
            },
            trailingIcon = {
                if (!enabled) {
                    IconButton(onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                            onClearText()
                        } else {
                            enabled = !enabled
                            clicked = false
                            onCloseSearchView()
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
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
                    clicked = false
                    onSearchClicked(text)
                    focusManager.clearFocus()
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
    AnimatedVisibility(visible = visible) {
        IconButton(
            modifier = Modifier.size(36.dp),
            onClick = { onFilterClicked(keyboardController) }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                contentDescription = "filter",
                tint = MaterialTheme.colors.primary
            )
        }
    }

}