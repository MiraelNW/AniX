package com.miraeldev.signup.presentation.codeVerifyScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miraeldev.extensions.noRippleEffectClick
import com.miraeldev.presentation.OutlinedOtpTextField
import com.miraeldev.presentation.Toolbar
import com.miraeldev.signup.R
import kotlinx.coroutines.delay


@Composable
fun CodeVerifyScreen(
    email: String,
    password: String,
    onBackPressed: () -> Unit,
    viewModel: CodeVerifyViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()

    var enabled by rememberSaveable { mutableStateOf(true) }

    val otpText by remember { viewModel.otpText }
    val updateTextAction: (String) -> Unit = remember { { viewModel.updateOtpText(it) } }
    val onCompleteAction: () -> Unit = remember {
        {
            viewModel.updateUser(email)
            viewModel.verifyOtpCode(otpText, email, password)
        }
    }
    val onSendNewCodeAction: () -> Unit = remember {
        {
            viewModel.sendNewOtpCode()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Toolbar(
            onBackPressed = onBackPressed,
            text = R.string.email_code_verify
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Code has been send to $email"
            )

            OutlinedOtpTextField(
                value = otpText,
                onValueChange = updateTextAction,
                onFilled = {
                    enabled = false
                    onCompleteAction()
                },
                requestFocus = true,
                clearFocusWhenFilled = false
            )

            CodeRecent(sendNewCode = onSendNewCodeAction)
        }

        VerifyCodeButton(
            enabled = enabled,
            onVerifyButtonClick = {
                enabled = false
                onCompleteAction()
            }
        )
    }
}

@Composable
private fun CodeRecent(sendNewCode: () -> Unit) {

    var timer by rememberSaveable { mutableIntStateOf(60) }

    val needSendNewCode by remember {
        derivedStateOf { timer == 0 }
    }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { timer }
            .collect {
                if (it > 0) {
                    delay(1000)
                    timer -= 1
                }

            }
    }

    Row {
        if (!needSendNewCode) {

            Text(
                text = "Resend code in "
            )
            Text(
                text = "$timer sec",
                color = MaterialTheme.colors.primary
            )

        } else {

            Text(
                modifier = Modifier.noRippleEffectClick {
                    timer = 60
                    sendNewCode()
                },
                text = "Send new code",
                color = MaterialTheme.colors.primary
            )

        }
    }
}


@Composable
private fun VerifyCodeButton(enabled: Boolean, onVerifyButtonClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 48.dp),
        enabled = enabled,
        onClick = onVerifyButtonClick,
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = MaterialTheme.colors.primary.copy(0.6f)
        )
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(R.string.verify_code),
            fontSize = 20.sp,
            color = Color.White
        )
    }
}