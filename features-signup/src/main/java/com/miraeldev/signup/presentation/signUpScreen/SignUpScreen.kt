package com.miraeldev.signup.presentation.signUpScreen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.miraeldev.extensions.noRippleEffectClick
import com.miraeldev.navigation.decompose.authComponent.signUpComponent.SignUpComponent
import com.miraeldev.presentation.EmailField
import com.miraeldev.presentation.ErrorValidField
import com.miraeldev.presentation.PasswordField
import com.miraeldev.presentation.Toolbar
import com.miraeldev.signup.R

@Composable
fun SignUpScreen(component: SignUpComponent) {
    val model by component.model.collectAsStateWithLifecycle()

    val loginFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    var isLoginInFocus by rememberSaveable { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current

    val clearFocus = remember { { focusManager.clearFocus() } }
    val moveFocusDown: KeyboardActionScope.() -> Unit =
        remember { { focusManager.moveFocus(FocusDirection.Down) } }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                component.onChangeImage(uri.toString())
            }
        }
    )

    BackHandler(onBack = component::onBackClick)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .systemBarsPadding(),
    ) {
        Toolbar(
            onBackPressed = component::onBackClick,
            text = R.string.sign_up
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier.noRippleEffectClick {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape),
                        model = model.image.ifEmpty { R.drawable.ic_placeholder },
                        placeholder = painterResource(id = R.drawable.ic_placeholder),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = stringResource(R.string.profile_image)
                    )
                    Icon(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 12.dp),
                        imageVector = Icons.Default.Edit,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = stringResource(R.string.edit_icon)
                    )
                }

                NameField(
                    text = { model.username },
                    onValueChange = component::onChangeUsername,
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )

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
                                    component.refreshEmailError()
                                }
                            },
                        text = { model.email },
                        isLoginError = model.signUpError.emailError,
                        onNext = moveFocusDown,
                        onChange = component::onChangeEmail

                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = model.signUpError.emailError,
                        error = stringResource(R.string.invalid_mail)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordField(
                        text = { model.password },
                        isPasswordError = model.signUpError.passwordError,
                        onChange = component::onChangePassword,
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(passwordFocusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    isLoginInFocus = true
                                    component.refreshPasswordError()
                                }
                            }
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = model.signUpError.networkError,
                        error = stringResource(R.string.incorrect_password_or_email_entered)
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = model.signUpError.passwordLengthError,
                        error = stringResource(R.string.password_must_be_more_than_6_characters)
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = model.signUpError.passwordHasCapitalizedLetterError,
                        error = stringResource(R.string.the_password_must_contain_capital_letters)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordField(
                        text = { model.repeatedPassword },
                        isPasswordError = model.signUpError.repeatedPasswordError,
                        onChange = component::onChangeRepeatedPassword,
                        iconRes = R.drawable.ic_lock,
                        iconSize = 20.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(passwordFocusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    isLoginInFocus = true
                                    component.refreshRepeatedPasswordError()
                                }
                            }
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = model.signUpError.repeatedPasswordError,
                        error = stringResource(R.string.password_mismatch)
                    )

//                    ErrorValidField(
//                        modifier = Modifier.padding(start = 16.dp),
//                        isError = model.signUpError.networkError,
//                        error = stringResource(R.string.error_try_again_later)
//                    )
                }
            }

            RegistrationButton(
                onSignUpButtonClick = {
                    clearFocus()
                    component.onSignUpClick(
                        model.image,
                        model.username,
                        model.email,
                        model.password,
                        model.repeatedPassword
                    )
                }
            )
        }


    }
}

@Composable
private fun NameField(
    text: () -> String,
    onValueChange: (String) -> Unit,
    onNext: KeyboardActionScope.() -> Unit,
) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text(),
        onValueChange = onValueChange,
        label = { Text(text = stringResource(R.string.your_name), fontSize = 14.sp) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = onNext
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_your_name),
                color = MaterialTheme.colors.onBackground.copy(0.7f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            unfocusedLabelColor = MaterialTheme.colors.onBackground.copy(0.7f),
            focusedLabelColor = MaterialTheme.colors.primary,
            errorBorderColor = Color.Red,
            backgroundColor = MaterialTheme.colors.onSecondary,
        ),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
private fun RegistrationButton(
    onSignUpButtonClick: () -> Unit
) {

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 48.dp),
        onClick = onSignUpButtonClick,
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        )
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(id = R.string.sign_up),
            fontSize = 20.sp,
            color = Color.White
        )
    }
}
