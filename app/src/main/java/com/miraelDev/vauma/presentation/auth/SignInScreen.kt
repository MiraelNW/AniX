package com.miraelDev.vauma.presentation.auth

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.NoRippleInteractionSource
import com.miraelDev.vauma.exntensions.pressClickEffect

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToSignUpScreen: () -> Unit,
    signIn: () -> Unit,
) {
    Surface {
        var credentials by remember { mutableStateOf(Credentials()) }
        val context = LocalContext.current

        val loginText by viewModel.loginTextState
        val passwordText by viewModel.passwordTextState

        val loginFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }

        var isLoginInFocus by rememberSaveable { mutableStateOf(true) }

        var isLoginError by rememberSaveable { mutableStateOf(false) }
        var isPasswordError by rememberSaveable { mutableStateOf(false) }

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
                modifier = Modifier.size(200.dp),
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
                LoginField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(loginFocusRequester)
                        .onFocusEvent {
                            if (it.hasFocus) {
                                isLoginError = true
                                isLoginError = false
                            }
                        },
                    value = TextFieldValue(
                        loginText,
                        selection = TextRange(loginText.length)
                    ),
                    isLoginError = isLoginError,
                    focusManager = focusManager,
                    onChange = viewModel::updateLoginTextState

                )
                Spacer(modifier = Modifier.height(14.dp))
                PasswordField(
                    value = TextFieldValue(
                        passwordText,
                        selection = TextRange(passwordText.length)
                    ),
                    isPasswordError = isPasswordError,
                    onChange = viewModel::updatePasswordTextState,
                    submit = {
                        if (!checkCredentials(credentials, context)) credentials = Credentials()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester)
                        .onFocusEvent {
                            if (it.isFocused) {
                                isLoginInFocus = true
                                isLoginError = false
                                isPasswordError = false
                            }
                        }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = {
                            focusManager.clearFocus()
                            isLoginError = false
                            isPasswordError = false
                            navigateToSignUpScreen()
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
                            if (loginText.isEmpty() || passwordText.isEmpty()) {
                                loginFocusRequester.freeFocus()
                                isLoginError = loginText.isEmpty()
                                isPasswordError = passwordText.isEmpty()
                            } else {
                                signIn()
                            }
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
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.pressClickEffect { },
                    text = stringResource(R.string.forgot_the_password),
                    color = MaterialTheme.colors.primary
                )
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
    }
}

fun checkCredentials(creds: Credentials, context: Context): Boolean {
    return true
}

data class Credentials(
    var login: String = "",
    var pwd: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return login.isNotEmpty() && pwd.isNotEmpty()
    }
}

@Composable
fun LoginField(
    value: TextFieldValue,
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
        onValueChange = { onChange(it.text) },
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
    value: TextFieldValue,
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
        onValueChange = { onChange(it.text) },
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