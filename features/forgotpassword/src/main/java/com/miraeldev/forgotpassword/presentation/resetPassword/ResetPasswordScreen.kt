package com.miraeldev.forgotpassword.presentation.resetPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.designsystem.ErrorValidField
import com.miraeldev.designsystem.PasswordField
import com.miraeldev.designsystem.Toolbar
import com.miraeldev.forgotpassword.R
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.ResetPasswordComponent

@Composable
fun ResetPasswordScreen(
    component: ResetPasswordComponent,
    email: String
) {

    val model by component.model.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Toolbar(
            onBackPressed = component::onBackClicked,
            text = R.string.create_password
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(300.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_password_image),
                contentDescription = null
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                PasswordField(
                    text = { model.password },
                    isPasswordError = model.resetPasswordErrorModel.passwordError,
                    onChange = component::onPasswordChange,
                    imeAction = ImeAction.Next,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                component.refreshPasswordError()
                            }
                        },
                )

                ErrorValidField(
                    modifier = Modifier.padding(start = 16.dp),
                    isError = model.resetPasswordErrorModel.passwordLengthError,
                    error = stringResource(R.string.password_must_be_more_than_6_characters)
                )

                ErrorValidField(
                    modifier = Modifier.padding(start = 16.dp),
                    isError = model.resetPasswordErrorModel.passwordHasCapitalizedLetterError,
                    error = stringResource(R.string.the_password_must_contain_capital_letters)
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                PasswordField(
                    text = { model.repeatedPassword },
                    isPasswordError = model.resetPasswordErrorModel.repeatedPasswordError,
                    onChange = component::onRepeatedPasswordChange,
                    iconRes = R.drawable.ic_lock,
                    placeholder = stringResource(R.string.repeat_password),
                    iconSize = 20.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                component.refreshRepeatedPasswordError()
                            }
                        },
                )

                ErrorValidField(
                    modifier = Modifier.padding(start = 16.dp),
                    isError = model.resetPasswordErrorModel.repeatedPasswordError,
                    error = stringResource(R.string.password_mismatch)
                )

                ErrorValidField(
                    modifier = Modifier.padding(start = 16.dp),
                    isError = model.resetPasswordErrorModel.networkError,
                    error = stringResource(R.string.network_error)
                )
            }
        }

        ContinueButton(
            modifier = Modifier,
            onVerifyButtonClick = {
                focusManager.clearFocus()
                component.saveNewPassword(
                    email,
                    model.password,
                    model.repeatedPassword
                )
            }
        )
    }
}

@Composable
private fun ContinueButton(modifier: Modifier, onVerifyButtonClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(top = 48.dp),
        onClick = onVerifyButtonClick,
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = MaterialTheme.colors.primary.copy(0.6f)
        )
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(R.string.continue_message),
            fontSize = 20.sp,
            color = Color.White
        )
    }
}