package com.miraelDev.vauma.presentation.auth.signInScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.NoRippleInteractionSource
import com.miraelDev.vauma.exntensions.pressClickEffect

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onReadyToDrawStartScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        onReadyToDrawStartScreen()
    }

    Surface {

        val loginText by viewModel.loginTextState
        val passwordText by viewModel.passwordTextState

        val loginFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }

        var isLoginInFocus by rememberSaveable { mutableStateOf(true) }

        val isEmailError by viewModel.isEmailError.collectAsStateWithLifecycle()
        val isPasswordError by viewModel.isPasswordError.collectAsStateWithLifecycle()

        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            Image(
                modifier = Modifier.size(220.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_app_icon),
                contentDescription = "app icon"
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.log_in_account),
                color = MaterialTheme.colors.primary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    LoginField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(loginFocusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    viewModel.refreshEmailError()
                                }
                            },
                        value = loginText,
                        isLoginError = isEmailError,
                        focusManager = focusManager,
                        onChange = viewModel::updateLoginTextState

                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = isEmailError,
                        error = "Недопустимая почта"
                    )
                }


                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordField(
                        value = passwordText,
                        isPasswordError = !isPasswordError.successful,
                        onChange = viewModel::updatePasswordTextState,
                        submit = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(passwordFocusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    isLoginInFocus = true
                                    viewModel.refreshPasswordError()
                                }
                            }
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = !isPasswordError.hasMinimum,
                        error = "Пароль должен быть больше 6 символов"
                    )

                    ErrorValidField(
                        modifier = Modifier.padding(start = 16.dp),
                        isError = !isPasswordError.hasCapitalizedLetter,
                        error = "Пароль должен содержать заглавные буквы"
                    )
                }



                Spacer(modifier = Modifier.height(20.dp))
                SignInAndSignUpButtons(
                    focusManager = focusManager,
                    onSignInClick = {
                        val isEmailValid = viewModel.isEmailValid()
                        val isPasswordValid = viewModel.isPasswordValid()
                        if (!isEmailError || !isPasswordValid) {
                            loginFocusRequester.freeFocus()
                        } else {
                            viewModel.signIn(loginText, passwordText)
                        }
                    },
                    onSignUpClick = {
                        viewModel.refreshEmailError()
                        viewModel.refreshPasswordError()
                        navigateToSignUpScreen()
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ForgetPassword(onForgetPasswordClick = {})
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .background(Color.Black.copy(0.1f))
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.enter_with),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(0.5f)
                )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .background(Color.Black.copy(0.1f))
                )
            }

            SignInSocialMedia()
        }
    }
}

@Composable
private fun ErrorValidField(modifier: Modifier, isError: Boolean, error: String) {

    AnimatedVisibility(modifier = modifier, visible = isError) {
        Text(text = error, color = Color.Red, fontSize = 14.sp)
    }

}

@Composable
private fun SignInSocialMedia() {
    Row(
        modifier = Modifier.fillMaxWidth(0.8f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            interactionSource = NoRippleInteractionSource()
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = R.drawable.ic_telegram),
                contentDescription = null
            )
        }
        IconButton(
            onClick = { /*TODO*/ },
            interactionSource = NoRippleInteractionSource()
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_google),
                contentDescription = null
            )
        }
        IconButton(
            onClick = { /*TODO*/ },
            interactionSource = NoRippleInteractionSource()
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_vk),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ForgetPassword(onForgetPasswordClick: () -> Unit) {
    Text(
        modifier = Modifier.pressClickEffect(onClick = onForgetPasswordClick),
        text = stringResource(R.string.forgot_the_password),
        color = MaterialTheme.colors.primary
    )
}

@Composable
private fun SignInAndSignUpButtons(
    focusManager: FocusManager,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        OutlinedButton(
            onClick = {
                focusManager.clearFocus()
                onSignUpClick()

            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = MaterialTheme.colors.primary
            )
        ) {
            Text(stringResource(R.string.sign_up), fontSize = 18.sp)
        }

        OutlinedButton(
            onClick = {
                focusManager.clearFocus()
                onSignInClick()
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.sign_in), fontSize = 18.sp)
        }
    }
}

@Composable
fun LoginField(
    value: String,
    isLoginError: Boolean,
    focusManager: FocusManager,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Введите Вашу почту"
) {
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Email,
            contentDescription = "",
            tint = if (isLoginError) Color.Red else MaterialTheme.colors.primary
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it) },
        modifier = modifier,
        isError = isLoginError,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = Color.Red,
            backgroundColor = Color.White,
        ),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    isPasswordError: Boolean,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Введите пароль"
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_key),
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
        value = value,
        onValueChange = { onChange(it) },
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isPasswordError,
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
                backgroundColor = Color.White
            ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}