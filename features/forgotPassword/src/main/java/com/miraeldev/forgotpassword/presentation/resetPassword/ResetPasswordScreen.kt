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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.forgotpassword.R
import com.miraeldev.presentation.ErrorValidField
import com.miraeldev.presentation.PasswordField
import com.miraeldev.presentation.Toolbar

@Composable
fun ResetPasswordScreen(
    onBackPressed: () -> Unit,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {

    val newPassword = remember { { viewModel.newPassword.value } }
    val repeatedPassword = remember { { viewModel.repeatedPassword.value } }

    val isPasswordError by viewModel.isPasswordError.collectAsStateWithLifecycle()
    val isPasswordNotEqualsError by viewModel.isPasswordNotEqualsError.collectAsStateWithLifecycle()
    val onNewPasswordChange: (String) -> Unit = remember {
        {
            viewModel.refreshPasswordError()
            viewModel.updateNewPasswordText(it)
        }
    }
    val onContinueButtonClick = remember {
        {
            val newPasswordValid = viewModel.isNewPasswordValid()
            val passwordEquals = viewModel.isPasswordEquals()
            if (newPasswordValid && passwordEquals) {
                viewModel.saveNewPassword()
            }
        }
    }

    val onRepeatedPasswordChange: (String) -> Unit =
        remember { { viewModel.updateRepeatedPasswordText(it) } }

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
            onBackPressed = onBackPressed,
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
                    text = newPassword,
                    isPasswordError = !isPasswordError.successful,
                    onChange = onNewPasswordChange,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                PasswordField(
                    text = repeatedPassword,
                    isPasswordError = !isPasswordNotEqualsError,
                    onChange = onRepeatedPasswordChange,
                    iconRes = R.drawable.ic_lock,
                    placeholder = stringResource(R.string.repeat_password),
                    iconSize = 20.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                ErrorValidField(
                    modifier = Modifier.padding(start = 16.dp),
                    isError = !isPasswordNotEqualsError,
                    error = stringResource(R.string.password_mismatch)
                )
            }
        }

        ContinueButton(
            modifier = Modifier,
            onVerifyButtonClick = onContinueButtonClick
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