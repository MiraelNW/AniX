package com.miraelDev.vauma.navigation.authComponent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.EmailChooseComponent
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.ResetPasswordComponent
import com.miraeldev.navigation.decompose.authComponent.signUpComponent.SignUpComponent
import com.miraeldev.signin.presentation.signInComponent.SignInComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.CodeVerifyComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.CodeVerifyRPComponent

interface AuthRootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        class SignIn(val component: SignInComponent) : Child
        class SignUp(val component: SignUpComponent) : Child
        class CodeVerify(
            val component: CodeVerifyComponent,
            val email: String,
            val password: String
        ) : Child
        class EmailChoose(val component: EmailChooseComponent) : Child
        class CodeVerifyResetPassword(val component: CodeVerifyRPComponent, val email: String) : Child
        class ResetPassword(val component: ResetPasswordComponent, val email: String) : Child
    }
}