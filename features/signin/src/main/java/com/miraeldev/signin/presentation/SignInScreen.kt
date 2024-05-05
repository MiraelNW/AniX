package com.miraeldev.signin.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.miraeldev.designsystem.EmailField
import com.miraeldev.designsystem.ErrorValidField
import com.miraeldev.designsystem.PasswordField
import com.miraeldev.extensions.NoRippleInteractionSource
import com.miraeldev.extensions.pressClickEffect
import com.miraeldev.signin.R
import com.miraeldev.signin.presentation.signInComponent.SignInComponent
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.launch

private const val GOOGLE_AUTH_REQUEST_CODE = 1

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignInScreen(component: SignInComponent) {
    val model by component.model.collectAsStateWithLifecycle()

    Surface {

        val loginFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }

        val focusManager = LocalFocusManager.current
        val context = LocalContext.current

        val clearFocus = remember { { focusManager.clearFocus() } }
        val moveFocusDown: KeyboardActionScope.() -> Unit =
            remember { { focusManager.moveFocus(FocusDirection.Down) } }

        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        val googleAuthResultLauncher = rememberLauncherForActivityResult(
            contract = AuthResultContract(),
            onResult = { task ->
                try {
                    val account = task?.getResult(ApiException::class.java)
                    if (account != null) {
                        account.idToken?.let { component.authViaGoogle(it) }
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(context.getString(R.string.google_auth_went_wrong))
                        }
                    }
                } catch (e: ApiException) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.google_auth_went_wrong))
                    }
                }
            }
        )

        val vkAuthResultLauncher = rememberLauncherForActivityResult(
            contract = VK.getVKAuthActivityResultContract(),
            onResult = {
                when (it) {
                    is VKAuthenticationResult.Success -> {
                        component.authViaVk(
                            it.token.accessToken,
                            it.token.userId.toString(),
                            it.token.email
                        )
                    }

                    is VKAuthenticationResult.Failed -> {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(context.getString(R.string.vk_auth_went_wrong))
                        }
                    }
                }
            }
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) {
                    Snackbar(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                        snackbarData = it,
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onBackground
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Image(
                    modifier = Modifier.size(150.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_app_icon),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = stringResource(R.string.app_icon)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(R.string.log_in_account),
                        color = MaterialTheme.colors.primary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
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
                            isLoginError = model.signInError.emailError,
                            onNext = moveFocusDown,
                            onChange = { component.onEmailChanged(it) }

                        )

                        ErrorValidField(
                            modifier = Modifier.padding(start = 16.dp),
                            isError = model.signInError.emailError,
                            error = stringResource(id = R.string.invalid_mail)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        PasswordField(
                            text = { model.password },
                            isPasswordError = model.signInError.passwordError,
                            onChange = { component.onPasswordChanged(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(passwordFocusRequester)
                                .onFocusEvent {
                                    if (it.isFocused) {
                                        component.refreshPasswordError()
                                    }
                                }
                        )

                        ErrorValidField(
                            modifier = Modifier.padding(start = 16.dp),
                            isError = model.signInError.networkError,
                            error = stringResource(R.string.incorrect_password_or_email_entered)
                        )

                        ErrorValidField(
                            modifier = Modifier.padding(start = 16.dp),
                            isError = model.signInError.passwordLengthError,
                            error = stringResource(R.string.password_must_be_more_than_6_characters)
                        )

                        ErrorValidField(
                            modifier = Modifier.padding(start = 16.dp),
                            isError = model.signInError.passwordHasCapitalizedLetterError,
                            error = stringResource(R.string.the_password_must_contain_capital_letters)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    SignInAndSignUpButtons(
                        onSignInClick = {
                            clearFocus()
                            component.onSignInClick(model.email, model.password)
                        },
                        onSignUpClick = {
                            clearFocus()
                            component.onSignUpClick()
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ForgetPassword(onForgetPasswordClick = component::forgetPasswordClick)
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .weight(1f)
                                .background(MaterialTheme.colors.onBackground.copy(0.1f))
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.enter_with),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onBackground.copy(0.5f)
                        )
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .weight(1f)
                                .background(MaterialTheme.colors.onBackground.copy(0.1f))
                        )
                    }

                    SignInSocialMedia(
                        googleAuthResultLauncher = googleAuthResultLauncher,
                        vkAuthResultLauncher = vkAuthResultLauncher
                    )
                }
            }
        }
    }
}

@Composable
private fun SignInSocialMedia(
    googleAuthResultLauncher: ManagedActivityResultLauncher<Int, Task<GoogleSignInAccount>?>,
    vkAuthResultLauncher: ManagedActivityResultLauncher<Collection<VKScope>, VKAuthenticationResult>
) {

    Row(
        modifier = Modifier.fillMaxWidth(0.8f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        IconButton(
            onClick = { vkAuthResultLauncher.launch(listOf(VKScope.WALL, VKScope.EMAIL)) },
            interactionSource = NoRippleInteractionSource()
        ) {
            Image(
                modifier = Modifier.size(56.dp),
                painter = painterResource(id = R.drawable.ic_vk),
                contentDescription = null
            )
        }

        IconButton(
            onClick = { googleAuthResultLauncher.launch(GOOGLE_AUTH_REQUEST_CODE) },
            interactionSource = NoRippleInteractionSource()
        ) {
            Image(
                modifier = Modifier.size(56.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_google),
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
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        OutlinedButton(
            onClick = onSignUpClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary)
        ) {
            Text(stringResource(R.string.sign_up), fontSize = 18.sp)
        }

        OutlinedButton(
            onClick = onSignInClick,
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
