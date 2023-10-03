package com.miraelDev.vauma.presentation.accountScreen.settings.editProfileScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.noRippleEffectClick
import com.miraelDev.vauma.presentation.mainScreen.LocalOrientation

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onChangeClick: () -> Unit,
    incorrectCurrentPasswordError: Boolean,
    isPasswordsValid: Boolean,
    newPassword: String,
    currentPassword: String,
    repeatedPassword: String,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onRepeatedPasswordChange: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    val orientationLandscape = LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE

    var isNewPasswordError by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleEffectClick(onClick = { focusManager.clearFocus() })
            .background(Color.Black.copy(alpha = 0.5f))
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .systemBarsPadding()
                .padding(vertical = if (orientationLandscape) 0.dp else 32.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_password),
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp),
                    fontSize = 24.sp,
                )
                ChangePasswordField(
                    modifier = Modifier.fillMaxWidth(),
                    value = currentPassword,
                    onChange = onCurrentPasswordChange,
                    isPasswordValid = incorrectCurrentPasswordError,
                    submit = { /*TODO*/ },
                    iconRes = R.drawable.ic_key,
                    placeholder = "Enter the current password"
                )

                ChangePasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                isNewPasswordError = false
                            }
                        },
                    value = newPassword,
                    onChange = onNewPasswordChange,
                    isPasswordValid = isNewPasswordError,
                    submit = { /*TODO*/ },
                    iconRes = R.drawable.ic_key,
                    placeholder = "Enter the new password"
                )

                ChangePasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                isNewPasswordError = false
                            }
                        },
                    value = repeatedPassword,
                    onChange = onRepeatedPasswordChange,
                    isPasswordValid = isNewPasswordError,
                    submit = { /*TODO*/ },
                    iconRes = R.drawable.ic_key,
                    placeholder = "Repeat new password"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = onDismiss,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = MaterialTheme.colors.primary
                        )
                    ) {
                        Text(text = "Cancel", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (newPassword == repeatedPassword && newPassword.length > 6) {
                                onDismiss()
                            } else {
                                isNewPasswordError = true
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Change", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun ChangePasswordField(
    value: String,
    onChange: (String) -> Unit,
    isPasswordValid: Boolean,
    iconRes: Int,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = "",
            tint = if (isPasswordValid) Color.Red else MaterialTheme.colors.primary
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
                tint = if (isPasswordValid) Color.Red else MaterialTheme.colors.primary
            )
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isPasswordValid,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { submit() }
        ),
        placeholder = { Text(placeholder) },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults
            .outlinedTextFieldColors(
                errorBorderColor = Color.Red,
                backgroundColor = MaterialTheme.colors.background,
                placeholderColor = MaterialTheme.colors.onBackground.copy(0.4f)
            ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}
