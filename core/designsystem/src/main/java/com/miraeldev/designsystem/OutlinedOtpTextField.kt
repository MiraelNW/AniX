
package com.miraeldev.designsystem

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedOtpTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    length: Int = 4,
    onFilled: (String) -> Unit = {},
    errorMessage: String? = null,
    textStyle: TextStyle = TextStyle.Default.copy(fontSize = 48.sp),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    requestFocus: Boolean,
    clearFocusWhenFilled: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val updatedOnValueChange by rememberUpdatedState(onValueChange)
    val updatedOnFilled by rememberUpdatedState(onFilled)

    val code by remember(value) {
        mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    }

    DisposableEffect(requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
        onDispose { }
    }

    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            val customTextSelectionColors = TextSelectionColors(
                handleColor = Color.Transparent,
                backgroundColor = Color.Transparent,
            )

            CompositionLocalProvider(
                LocalTextSelectionColors provides customTextSelectionColors
            ) {
                BasicTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester = focusRequester)
                        .fillMaxWidth(),
                    value = code,
                    onValueChange = {
                        if (!it.text.isDigitsOnly() || it.text.length > length)
                            return@BasicTextField

                        updatedOnValueChange(it.text)

                        if (it.text.length == length) {
                            keyboardController?.hide()
                            if (clearFocusWhenFilled) {
                                focusRequester.freeFocus()
                                focusManager.clearFocus()
                            }
                            updatedOnFilled(it.text)
                        }
                    },
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = textStyle,
                    enabled = enabled,
                    readOnly = readOnly,
                    decorationBox = {
                        OtpInputDecoration(
                            code = code.text,
                            length = length,
                            textStyle = textStyle,
                            enabled = enabled,
                            isError = !errorMessage.isNullOrBlank(),
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun OtpInputDecoration(
    modifier: Modifier = Modifier,
    code: String,
    length: Int,
    textStyle: TextStyle,
    enabled: Boolean,
    isError: Boolean,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            for (i in 0 until length) {
                val text = if (i < code.length) code[i].toString() else ""
                OtpEntry(
                    modifier = Modifier.weight(1f, fill = false),
                    text = text,
                    textStyle = textStyle,
                    enabled = enabled,
                    isError = isError,
                )
            }
        }
    }
}

@Composable
private fun OtpEntry(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    enabled: Boolean,
    isError: Boolean,
) {

    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> MaterialTheme.colors.error
            !enabled -> MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            text.isNotEmpty() -> MaterialTheme.colors.primary
            else -> MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
        },
        label = "textColor"
    )

    Box(
        modifier = modifier
            .width(74.dp)
            .height(66.dp)
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        val textColor by animateColorAsState(
            targetValue = when {
                isError -> MaterialTheme.colors.error
                !enabled -> textStyle.color.copy(alpha = ContentAlpha.disabled)
                else -> textStyle.color
            },
            label = "textColor"
        )

        AnimatedContent(
            modifier = Modifier
                .fillMaxWidth(),
            targetState = text,
            transitionSpec = {
                ContentTransform(
                    targetContentEnter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
                    initialContentExit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
                    sizeTransform = null
                )
            },
            contentAlignment = Alignment.Center,
            label = "textVisibility"
        ) { text ->
            if (text.isNotBlank()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = text,
                    color = textColor,
                    style = textStyle,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
            }
        }
    }
}
