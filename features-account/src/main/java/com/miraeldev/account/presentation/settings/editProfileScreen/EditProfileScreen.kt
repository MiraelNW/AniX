package com.miraeldev.account.presentation.settings.editProfileScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miraeldev.account.R
import com.miraeldev.account.presentation.settings.editProfileScreen.EditProfileComponent.EditProfileComponent
import com.miraeldev.extensions.NoRippleInteractionSource
import com.miraeldev.extensions.noRippleEffectClick
import com.miraeldev.presentation.EmailField
import com.miraeldev.presentation.ErrorValidField
import com.miraeldev.presentation.Toolbar
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun EditProfileScreen(component: EditProfileComponent) {
    val model by component.model.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var shouldShowChangePasswordDialog by rememberSaveable { mutableStateOf(false) }

    val moveFocusDown: KeyboardActionScope.() -> Unit =
        remember { { focusManager.moveFocus(FocusDirection.Down) } }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                component.onChangeImage(it.toString())
            }

        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
        ) {
            Toolbar(
                onBackPressed = component::onBackClick,
                text = R.string.edit_profile
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
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

                        GlideImage(
                            modifier = Modifier
                                .size(160.dp)
                                .clip(CircleShape),
                            imageModel = { model.userModel.image.ifEmpty { R.drawable.ic_placeholder } },
                            requestOptions = {
                                RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                            },
                            imageOptions = ImageOptions(
                                contentDescription = "profile image",
                                contentScale = ContentScale.Crop,
                            ),
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
                        onNext = moveFocusDown
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        EmailField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .onFocusEvent {
                                    if (it.isFocused) {
                                        component.refreshEmailError()
                                    }
                                },
                            text = { model.email },
                            isLoginError = model.editProfileErrorModel.emailError,
                            onNext = moveFocusDown,
                            onChange = component::onChangeEmail

                        )

                        ErrorValidField(
                            modifier = Modifier.padding(start = 16.dp),
                            isError = model.editProfileErrorModel.emailError,
                            error = stringResource(id = R.string.invalid_mail)
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(top = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ChangePassword(
                        onChangePasswordClick = {
                            shouldShowChangePasswordDialog = true
                        }
                    )

                    UpdateButton(
                        onUpdateButtonClick = {
                            component.updateUserInfo(
                                model.image,
                                model.email,
                                model.username
                            )
                        }
                    )
                }
            }
        }

        if (shouldShowChangePasswordDialog) {
            ChangePasswordDialog(
                onDismiss = {
                    component.resetAllChanges()
                    shouldShowChangePasswordDialog = false
                },
                refreshPasswordError = component::refreshPasswordError,
                isPasswordError = model.editProfileErrorModel.passwordError,
                isPasswordNotEqualsError = model.editProfileErrorModel.repeatedPasswordError,
                onChangeClick = {
                    component.onChangePasswordClick(
                        model.currentPassword,
                        model.password,
                        model.repeatedPassword
                    )
                    shouldShowChangePasswordDialog = false
                },
                newPassword = { model.password },
                serverError = model.editProfileErrorModel.passwordNetworkError,
                currentPassword = { model.currentPassword },
                repeatedPassword = { model.repeatedPassword },
                onCurrentPasswordChange = component::onChangeCurrentPassword,
                onNewPasswordChange = component::onChangePassword,
                onRepeatedPasswordChange = component::onChangeRepeatedPassword
            )
        }
    }
}

@Composable
private fun ChangePassword(onChangePasswordClick: () -> Unit) {

    OutlinedButton(
        modifier = Modifier.fillMaxWidth(0.9f),
        onClick = onChangePasswordClick,
        interactionSource = remember { NoRippleInteractionSource() },
        shape = RoundedCornerShape(36.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground.copy(0.2f)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onSecondary,

            )
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_key),
            contentDescription = "",
            tint = MaterialTheme.colors.primary,

            )
        Text(
            modifier = Modifier.padding(6.dp),
            text = "Change password",
            color = MaterialTheme.colors.onBackground,
            fontSize = 18.sp
        )
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
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = onNext
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.edit_your_name),
                color = MaterialTheme.colors.onBackground.copy(0.5f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            errorBorderColor = Color.Red,
            backgroundColor = MaterialTheme.colors.onSecondary,
        ),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
private fun UpdateButton(
    onUpdateButtonClick: () -> Unit
) {

    OutlinedButton(
        modifier = Modifier.fillMaxWidth(0.9f),
        onClick = onUpdateButtonClick,
        shape = RoundedCornerShape(36.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        )
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(R.string.update_profile),
            fontSize = 20.sp,
            color = Color.White
        )
    }
}