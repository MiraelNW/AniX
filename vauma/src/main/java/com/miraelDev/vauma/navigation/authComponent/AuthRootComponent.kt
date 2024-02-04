package com.miraeldev.navigation.decompose.authComponent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraeldev.navigation.decompose.authComponent.signInComponent.SignInComponent
import com.miraeldev.navigation.decompose.authComponent.signUpComponent.SignUpComponent

interface AuthRootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        class SignIn(val component: SignInComponent) : Child
        class SignUp(val component: SignUpComponent) : Child
//        class CodeVerify(val component: ContactListComponent) : Child
//        class ForgotPassword(val component: EditContactComponent) : Child
    }
}