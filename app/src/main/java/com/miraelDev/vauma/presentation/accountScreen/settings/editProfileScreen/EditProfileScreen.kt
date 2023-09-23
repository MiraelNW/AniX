package com.miraelDev.vauma.presentation.accountScreen.settings.editProfileScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.noRippleEffectClick
import com.miraelDev.vauma.presentation.commonComposFunc.Toolbar
import com.miraelDev.vauma.ui.theme.DarkWhite
import com.miraelDev.vauma.ui.theme.DarkWhite700
import com.miraelDev.vauma.ui.theme.LightGreen
import com.miraelDev.vauma.ui.theme.LightWhite

@Composable
fun EditProfileScreen(
    onBackPressed: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {

    val nickName by viewModel.nickNameState
    val email by viewModel.emailState

    val focusManager = LocalFocusManager.current

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
                   modifier = Modifier.noRippleEffectClick{}
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape),
                        model = "https://gravatar.com/avatar/0143887282216779617c58f10181af2e?s=400&d=robohash&r=x",
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

            UpdateButton(onUpdateButtonClick = {

            })
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
//        isError = isLoginError,
//        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_your_name),
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
                text = stringResource(R.string.enter_your_email),
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