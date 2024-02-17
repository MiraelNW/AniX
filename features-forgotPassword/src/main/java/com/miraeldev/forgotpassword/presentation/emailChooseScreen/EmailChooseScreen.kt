package com.miraeldev.forgotpassword.presentation.emailChooseScreen

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.forgotpassword.R
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.EmailChooseComponent
import com.miraeldev.presentation.EmailField
import com.miraeldev.presentation.ErrorValidField
import com.miraeldev.presentation.Toolbar


@Composable
fun EmailChooseScreen(component: EmailChooseComponent) {

    val model by component.model.collectAsStateWithLifecycle()

    val loginFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

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
            onBackPressed = component::onBackClicked,
            text = R.string.choose_email
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_email_image),
                contentDescription = null
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                EmailField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(loginFocusRequester)
                        .onFocusEvent {
                            if (it.isFocused) {
                                component.refreshEmailError()
                            }
                        },
                    unfocusedBorderColor = Color.Transparent,
                    text = { model.email },
                    isLoginError = model.emailChooseErrorModel.emailNotValidError,
                    onNext = {},
                    onChange = component::onEmailChange

                )

                ErrorValidField(
                    modifier = Modifier.padding(start = 16.dp),
                    isError = model.emailChooseErrorModel.emailNotValidError,
                    error = stringResource(id = R.string.invalid_mail)
                )

                ErrorValidField(
                    modifier = Modifier.padding(start = 16.dp),
                    isError = model.emailChooseErrorModel.emailNotExistError,
                    error = stringResource(id = R.string.mail_not_exist)
                )
            }
        }

        ContinueButton(
            onVerifyButtonClick = {
                focusManager.clearFocus()
                component.checkEmailExist(model.email)
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