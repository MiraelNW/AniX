package com.miraelDev.vauma.presentation.auth.codeVerifyScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraelDev.vauma.R
import com.miraelDev.vauma.presentation.auth.EmailChooseScreen.EmailChooseViewModel
import com.miraelDev.vauma.presentation.auth.signInScreen.ErrorValidField
import com.miraelDev.vauma.presentation.commonComposFunc.EmailField
import com.miraelDev.vauma.presentation.commonComposFunc.Toolbar


@Composable
fun EmailChooseScreen(
    onBackPressed: () -> Unit,
    navigateToCodeVerify: (String) -> Unit,
    viewModel: EmailChooseViewModel = hiltViewModel()
) {

    val email by remember { viewModel.email }
    val loginFocusRequester = remember { FocusRequester() }
    val isEmailError by viewModel.isEmailError.collectAsStateWithLifecycle()
    val updateTextAction: (String) -> Unit = remember { { viewModel.updateOtpText(it) } }
    val refreshEmailErrorAction = remember { { viewModel.refreshEmailError() } }
    val onCompleteAction: () -> Unit = remember {
        {
            navigateToCodeVerify(email)
        }
    }
    val onSendNewCodeAction: () -> Unit = remember {
        {
//            viewModel.sendNewOtpCode()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Toolbar(
            onBackPressed = onBackPressed,
            text = R.string.forgot_password
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //image

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                EmailField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(loginFocusRequester)
                        .onFocusEvent {
                            if (it.isFocused) {
                                refreshEmailErrorAction()
                            }
                        },
                    text = { email },
                    isLoginError = isEmailError,
                    onNext = {},
                    onChange = updateTextAction

                )

                ErrorValidField(
                    modifier = Modifier.padding(start = 16.dp),
                    isError = isEmailError,
                    error = stringResource(id = R.string.invalid_mail)
                )
            }
        }

        ContinueButton(
            onVerifyButtonClick = {
                onCompleteAction()
            }
        )
    }
}

@Composable
private fun ContinueButton(onVerifyButtonClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
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