package com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.extensions.noRippleEffectClick
import com.miraeldev.forgotpassword.R
import com.miraeldev.presentation.OutlinedOtpTextField
import com.miraeldev.presentation.Toolbar
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.CodeVerifyRPComponent
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CodeVerifyRPScreen(
    component: CodeVerifyRPComponent,
    email: String
) {

    val model by component.model.collectAsStateWithLifecycle()

    var enabled by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .padding(horizontal = 6.dp),
        topBar = {
            Toolbar(
                onBackPressed = component::onBackClicked,
                text = R.string.email_code_verify
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Code has been send to $email",
                fontSize = 22.sp
            )

            OutlinedOtpTextField(
                value = model.otpText,
                onValueChange = component::onOtpChange,
                onFilled = { otp ->
                    enabled = false
                    component.verifyOtp(otp)
                },
                requestFocus = true,
                clearFocusWhenFilled = false
            )

            CodeRecent(sendNewCode = component::sendNewOtp)
        }
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
                text = "Resend code in ",
                fontSize = 18.sp
            )
            Text(
                text = "$timer sec",
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp
            )

        } else {

            Text(
                modifier = Modifier.noRippleEffectClick {
                    timer = 60
                    sendNewCode()
                },
                text = "Send new code",
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary
            )

        }
    }
}