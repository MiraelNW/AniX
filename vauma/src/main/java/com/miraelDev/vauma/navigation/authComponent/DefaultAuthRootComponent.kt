package com.miraelDev.vauma.navigation.authComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.DefaultEmailChooseComponent
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.DefaultResetPasswordComponent
import com.miraeldev.signin.presentation.signInComponent.DefaultSignInComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.DefaultCodeVerifyComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.DefaultCodeVerifyRPComponent
import com.miraeldev.signup.presentation.signUpScreen.signUpComponent.DefaultSignUpComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

class DefaultAuthRootComponent @AssistedInject constructor(
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("logIn") private val logIn: () -> Unit,
    private val signInComponentFactory: DefaultSignInComponent.Factory,
    private val signUpComponentFactory: DefaultSignUpComponent.Factory,
    private val codeVerifyComponentFactory: DefaultCodeVerifyComponent.Factory,
    private val codeVerifyRPComponentFactory: DefaultCodeVerifyRPComponent.Factory,
    private val emailChooseComponentFactory: DefaultEmailChooseComponent.Factory,
    private val resetPasswordComponent: DefaultResetPasswordComponent.Factory
) : AuthRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AuthRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.SignIn,
        key = "AuthRootStack",
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): AuthRootComponent.Child {
        return when (config) {
            is Config.SignIn -> {
                val component = signInComponentFactory.create(
                    componentContext = componentContext,
                    onSignUpClicked = {
                        navigation.push(Config.SignUp)
                    },
                    onForgetPasswordClick = {
                        navigation.push(Config.EmailChoose)
                    },
                    logIn = logIn
                )
                AuthRootComponent.Child.SignIn(component)
            }

            is Config.SignUp -> {
                val component = signUpComponentFactory.create(
                    componentContext = componentContext,
                    onSignUpClicked = { email, password ->
                        navigation.push(Config.CodeVerify(email, password))
                    },
                    onBackClicked = {
                        navigation.pop()
                    }
                )
                AuthRootComponent.Child.SignUp(component)
            }

            is Config.CodeVerify -> {
                val component = codeVerifyComponentFactory.create(
                    componentContext = componentContext,
                    onBackClicked = {
                        navigation.pop()
                    }
                )
                AuthRootComponent.Child.CodeVerify(component, config.email, config.password)
            }

            is Config.CodeVerifyForgotPassword -> {
                val component = codeVerifyRPComponentFactory.create(
                    componentContext = componentContext,
                    onBackClicked = {
                        navigation.pop()
                    },
                    onOtpVerified = {
                        navigation.push(Config.ResetPassword(config.email))
                    }
                )
                AuthRootComponent.Child.CodeVerifyResetPassword(component, config.email)
            }

            is Config.EmailChoose -> {
                val component = emailChooseComponentFactory.create(
                    componentContext = componentContext,
                    onEmailExist = { email ->
                        navigation.push(Config.CodeVerifyForgotPassword(email))
                    },
                    onBackClicked = {
                        navigation.pop()
                    }
                )
                AuthRootComponent.Child.EmailChoose(component)
            }

            is Config.ResetPassword -> {
                val component = resetPasswordComponent.create(
                    componentContext = componentContext,
                    onBackClicked = {
                        navigation.pop()
                    }
                )
                AuthRootComponent.Child.ResetPassword(component, config.email)
            }
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object SignIn : Config

        @Serializable
        data object SignUp : Config

        @Serializable
        data class CodeVerify(val email: String, val password: String) : Config

        @Serializable
        data object EmailChoose : Config

        @Serializable
        data class CodeVerifyForgotPassword(val email: String) : Config

        @Serializable
        data class ResetPassword(val email: String) : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("logIn") logIn: () -> Unit,
        ): DefaultAuthRootComponent
    }

}