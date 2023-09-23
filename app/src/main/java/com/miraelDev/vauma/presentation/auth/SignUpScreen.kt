package com.miraelDev.vauma.presentation.auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.noRippleEffectClick
import com.miraelDev.vauma.presentation.commonComposFunc.Toolbar

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onBackClicked: () ->Unit,
    signUp: () ->Unit,
) {
    val nickName by viewModel.nickNameState
    val email by viewModel.emailState
    val phoneNumber by viewModel.phoneNumberState
    val repeatedPassword by viewModel.repeatedPasswordState
    val password by viewModel.passwordState

    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isRepeatedPasswordError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    BackHandler(onBack = onBackClicked)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        Toolbar(
            onBackPressed = {},
            text = R.string.sign_up
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    modifier = Modifier.noRippleEffectClick {}
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape),
                        model = "https://gravatar.com/avatar/0143887282216779617c58f10181af2e?s=400&d=robohash&r=x",
                        placeholder = painterResource(id = R.drawable.ic_placeholder),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile image"
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
                    text = nickName,
                    onValueChange = viewModel::updateNickName,
                    focusManager = focusManager
                )

                EmailField(
                    modifier = Modifier
                        .onFocusEvent {
                            if (it.isFocused) {
                                isPasswordError = false
                                isEmailError = false
                                isRepeatedPasswordError = false
                            }
                        },
                    text = email,
                    onValueChange = viewModel::updateEmail,
                    isError = isEmailError,
                    focusManager = focusManager
                )

                PasswordField(
                    modifier = Modifier
                        .onFocusEvent {
                            if (it.isFocused) {
                                isPasswordError = false
                                isEmailError = false
                                isRepeatedPasswordError = false
                            }
                        },
                    value = TextFieldValue(
                        text = password,
                        selection = TextRange(password.length)
                    ),
                    onChange = viewModel::updatePassword,
                    focusManager = focusManager,
                    isError = isPasswordError,
                )

                RepeatedPasswordField(
                    modifier = Modifier
                        .onFocusEvent {
                            if (it.isFocused) {
                                isPasswordError = false
                                isEmailError = false
                                isRepeatedPasswordError = false
                            }
                        },
                    value = TextFieldValue(
                        text = repeatedPassword,
                        selection = TextRange(repeatedPassword.length)
                    ),
                    onChange = viewModel::updateRepeatedPassword,
                    isError = isRepeatedPasswordError,
                )
            }

            RegistrationButton(
                onRegistrationButtonClick = {
                    focusManager.clearFocus()
                    if (email.isEmpty() || password.isEmpty()
                        || repeatedPassword.isEmpty() || !viewModel.passwordIsEqual()
                    ) {
                        isEmailError = email.isEmpty()
                        isPasswordError = password.isEmpty() || !viewModel.passwordIsEqual()
                        isRepeatedPasswordError =
                            repeatedPassword.isEmpty() || !viewModel.passwordIsEqual()
                    }else{
                        viewModel.registrationUser()
                        signUp()
                    }
                }
            )
        }


    }
}

@Composable
private fun NameField(
    text: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.9f),
        value = text,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = R.string.enter_your_name)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_your_name),
                color = MaterialTheme.colors.onBackground.copy(0.3f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedLabelColor = MaterialTheme.colors.onBackground.copy(0.3f),
            focusedLabelColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Red,
            backgroundColor = MaterialTheme.colors.onSecondary,
        ),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
private fun EmailField(
    modifier: Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    focusManager: FocusManager,
) {

    val leadingIcon = @Composable {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_mail),
            contentDescription = "",
            tint = if (isError) Color.Red else MaterialTheme.colors.primary
        )
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.9f),
        value = text,
        onValueChange = onValueChange,
        isError = isError,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_your_email),
                color = MaterialTheme.colors.onBackground.copy(0.3f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Red,
            backgroundColor = MaterialTheme.colors.onSecondary,
        ),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
private fun PasswordField(
    modifier: Modifier,
    value: TextFieldValue,
    onChange: (String) -> Unit,
    focusManager: FocusManager,
    isError: Boolean,
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_key),
            contentDescription = "",
            tint = if (isError) Color.Red else MaterialTheme.colors.primary
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
                tint = if (isError) Color.Red else MaterialTheme.colors.primary
            )
        }
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.9f),
        value = value,
        onValueChange = { onChange(it.text) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_password),
                color = MaterialTheme.colors.onBackground.copy(0.3f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults
            .outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Red,
                backgroundColor = MaterialTheme.colors.onSecondary,
            ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
private fun RepeatedPasswordField(
    modifier: Modifier,
    value: TextFieldValue,
    onChange: (String) -> Unit,
    isError: Boolean,
) {

    val leadingIcon = @Composable {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_key),
            contentDescription = "",
            tint = if (isError) Color.Red else MaterialTheme.colors.primary
        )
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.9f),
        value = value,
        onValueChange = { onChange(it.text) },
        leadingIcon = leadingIcon,
        isError = isError,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.repeaet_password),
                color = MaterialTheme.colors.onBackground.copy(0.3f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults
            .outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Red,
                backgroundColor = MaterialTheme.colors.onSecondary,
            ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
private fun RegistrationButton(
    onRegistrationButtonClick: () -> Unit
) {

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 48.dp),
        onClick = onRegistrationButtonClick,
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
