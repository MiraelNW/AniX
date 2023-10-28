package com.miraeldev.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun EmailField(
    text: () -> String,
    isLoginError: Boolean,
    onNext: KeyboardActionScope.() -> Unit,
    onChange: (String) -> Unit,
    unfocusedBorderColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
    modifier: Modifier = Modifier,
    placeholder: String = "Введите Вашу почту"
) {

//    val value = remember(text){ text() }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Email,
            contentDescription = "",
            tint = if (isLoginError) Color.Red else MaterialTheme.colors.primary
        )
    }

    OutlinedTextField(
        value = text(),
        onValueChange = onChange,
        modifier = modifier,
        isError = isLoginError,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = onNext
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colors.onBackground.copy(0.5f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = unfocusedBorderColor,
            errorBorderColor = Color.Red,
            backgroundColor = MaterialTheme.colors.onSecondary,
        ),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    text: () -> String,
    onChange: (String) -> Unit,
    isPasswordError: Boolean,
    iconRes: Int = R.drawable.ic_key,
    iconSize: Dp = 24.dp,
    imeAction: ImeAction = ImeAction.Done,
    placeholder: String = "Введите пароль"
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = "",
            tint = if (isPasswordError) Color.Red else MaterialTheme.colors.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(
                    id =
                    if (isPasswordVisible) R.drawable.ic_open_eye else R.drawable.ic_close_eye
                ),
                contentDescription = "",
                tint = if (isPasswordError) Color.Red else MaterialTheme.colors.primary
            )
        }
    }

    OutlinedTextField(
        value = text(),
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isPasswordError,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = KeyboardType.Password
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colors.onBackground.copy(0.5f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults
            .outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                errorBorderColor = Color.Red,
                backgroundColor = MaterialTheme.colors.onSecondary
            ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}