package com.miraelDev.vauma.navigation.authComponent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.EmailChooseComponent
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.ResetPasswordComponent
import com.miraeldev.signin.presentation.signInComponent.SignInComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.CodeVerifyComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.CodeVerifyRPComponent
import com.miraeldev.signup.presentation.signUpScreen.signUpComponent.SignUpComponent

interface AuthRootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class SignIn(val component: SignInComponent) : Child
        data class SignUp(val component: SignUpComponent, val imageLoader: VaumaImageLoader) : Child
        data class CodeVerify(
            val component: CodeVerifyComponent,
            val email: String,
            val password: String
        ) : Child
        data class EmailChoose(val component: EmailChooseComponent) : Child
        data class CodeVerifyResetPassword(val component: CodeVerifyRPComponent, val email: String) : Child
        data class ResetPassword(val component: ResetPasswordComponent, val email: String) : Child
    }
}