package com.miraelDev.vauma.presentation.accountScreen.settings.editProfileScreen

import android.net.Uri
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.NoRippleInteractionSource
import com.miraelDev.vauma.exntensions.noRippleEffectClick
import com.miraelDev.vauma.presentation.commonComposFunc.Toolbar

@Composable
fun EditProfileScreen(
    onBackPressed: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {

    val nickName by viewModel.nickNameState
    val email by viewModel.emailState

    val focusManager = LocalFocusManager.current

    var shouldShowChangePasswordDialog by rememberSaveable { mutableStateOf(false) }

    val incorrectCurrentPassword by viewModel.isCurrentPasswordInvalid.collectAsStateWithLifecycle()
    val isPasswordValid by viewModel.isNewPasswordValid.collectAsStateWithLifecycle(true)

    val currentPassword by viewModel.currentPassword
    val newPassword by viewModel.newPassword
    val repeatedPassword by viewModel.repeatedPassword

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
        ) {
            Toolbar(
                onBackPressed = onBackPressed,
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
                            model = selectedImageUri,
                            placeholder = painterResource(id = R.drawable.ic_account),
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
                        text = email,
                        onValueChange = viewModel::updateEmail,
                        focusManager = focusManager
                    )
                }

                Column(
                    modifier = Modifier.padding(top=24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ChangePassword(
                        onChangePasswordClick = {
                            shouldShowChangePasswordDialog = true
                        }
                    )

                    UpdateButton(
                        onUpdateButtonClick = {
                            // save in db
                        }
                    )
                }
            }
        }

        if (shouldShowChangePasswordDialog) {
            ChangePasswordDialog(
                onDismiss = {
                    viewModel.resetAllChanges()
                    shouldShowChangePasswordDialog = false
                },
                onChangeClick = {

                },
                incorrectCurrentPasswordError = incorrectCurrentPassword,
                isPasswordsValid = isPasswordValid,
                newPassword = newPassword,
                currentPassword = currentPassword,
                repeatedPassword = repeatedPassword,
                onCurrentPasswordChange = viewModel::updateCurrentPassword,
                onNewPasswordChange = viewModel::updateNewPassword,
                onRepeatedPasswordChange = viewModel::updateRepeatedPassword
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
    text: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.9f),
        value = text,
        onValueChange = onValueChange,
//        isError = isLoginError,
//        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
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
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Red,
            backgroundColor = MaterialTheme.colors.onSecondary,
        ),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
private fun EmailField(
    text: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.9f),
        value = text,
        onValueChange = onValueChange,
//        isError = isLoginError,
//        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = {
            Text(
                text = "edit your email",
                color = MaterialTheme.colors.onBackground.copy(0.5f)
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