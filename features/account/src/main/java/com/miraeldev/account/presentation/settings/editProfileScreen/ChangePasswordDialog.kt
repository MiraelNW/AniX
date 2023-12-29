package com.miraeldev.account.presentation.settings.editProfileScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraeldev.account.R
import com.miraeldev.exntensions.noRippleEffectClick
import com.miraeldev.presentation.ErrorValidField
import com.miraeldev.presentation.PasswordField
import com.miraeldev.theme.LocalOrientation
import com.miraeldev.utils.PasswordValidationState

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onChangeClick: () -> Unit,
    refreshPasswordError: () -> Unit,
    checkNewPasswordValid: () -> Boolean,
    checkCurrentPasswordValid: () -> Boolean,
    checkPasswordEquals: () -> Boolean,
    isPasswordError: PasswordValidationState,
    isPasswordNotEqualsError: Boolean,
    serverError: Boolean,
    newPassword: () -> String,
    currentPassword: () -> String,
    repeatedPassword: () -> String,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onRepeatedPasswordChange: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    val orientationLandscape = LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE

    val clearFocus = remember { { focusManager.clearFocus() } }

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .noRippleEffectClick(onClick = clearFocus)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .background(Color.Black.copy(alpha = 0.5f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .systemBarsPadding()
                .padding(vertical = if (orientationLandscape) 0.dp else 32.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_password),
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp),
                    fontSize = 24.sp,
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordField(
                        text = currentPassword,
                        isPasswordError = !isPasswordError.successful,
                        onChange = onCurrentPasswordChange,
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    refreshPasswordError()
                                }
                            }
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = !isPasswordError.hasMinimum,
                        error = stringResource(R.string.wrong_current_password)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordField(
                        text = newPassword,
                        isPasswordError = !isPasswordError.successful,
                        onChange = onNewPasswordChange,
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    refreshPasswordError()
                                }
                            }
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = !isPasswordError.hasMinimum,
                        error = stringResource(R.string.password_must_be_more_than_6_characters)
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = !isPasswordError.hasCapitalizedLetter,
                        error = stringResource(R.string.the_password_must_contain_capital_letters)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordField(
                        text = repeatedPassword,
                        isPasswordError = !isPasswordNotEqualsError || serverError,
                        onChange = onRepeatedPasswordChange,
                        iconRes = R.drawable.ic_lock,
                        iconSize = 20.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    refreshPasswordError()
                                }
                            }
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = !isPasswordNotEqualsError,
                        error = stringResource(R.string.password_mismatch)
                    )
                }

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
                            val isCurrentPasswordValid = checkCurrentPasswordValid()
                            val isNewPasswordValid = checkNewPasswordValid()
                            val isPasswordsEquals = checkPasswordEquals()
                            if (isCurrentPasswordValid && isNewPasswordValid && isPasswordsEquals) {
                                onChangeClick()
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