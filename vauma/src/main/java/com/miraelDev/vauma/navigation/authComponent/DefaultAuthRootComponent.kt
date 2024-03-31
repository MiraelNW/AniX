package com.miraelDev.vauma.navigation.authComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.codeVerifyResetPasswordComponent.DefaultCodeVerifyRPComponentFactory
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.DefaultEmailChooseComponentFactory
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.DefaultResetPasswordComponentFactory
import com.miraeldev.models.LogIn
import com.miraeldev.signin.presentation.signInComponent.DefaultSignInComponentFactory
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.DefaultCodeVerifyComponentFactory
import com.miraeldev.signup.presentation.signUpScreen.signUpComponent.DefaultSignUpComponentFactory
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultAuthRootComponentFactory = (ComponentContext, VaumaImageLoader, LogIn) -> DefaultAuthRootComponent

@Inject
class DefaultAuthRootComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val imageLoader: VaumaImageLoader,
    @Assisted private val logIn: () -> Unit,
    private val signInComponentFactory: DefaultSignInComponentFactory,
    private val signUpComponentFactory: DefaultSignUpComponentFactory,
    private val codeVerifyComponentFactory: DefaultCodeVerifyComponentFactory,
    private val codeVerifyRPComponentFactory: DefaultCodeVerifyRPComponentFactory,
    private val emailChooseComponentFactory: DefaultEmailChooseComponentFactory,
    private val resetPasswordComponent: DefaultResetPasswordComponentFactory
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
                val component = signInComponentFactory(
                    componentContext,
                    { navigation.push(Config.SignUp) },
                    { navigation.push(Config.EmailChoose) },
                    logIn
                )
                AuthRootComponent.Child.SignIn(component)
            }

            is Config.SignUp -> {
                val component = signUpComponentFactory(
                    componentContext,
                    navigation::pop
                ) { email, password ->
                    navigation.push(Config.CodeVerify(email, password))
                }
                AuthRootComponent.Child.SignUp(component, imageLoader)
            }

            is Config.CodeVerify -> {
                val component = codeVerifyComponentFactory(
                    componentContext,
                    navigation::pop
                )
                AuthRootComponent.Child.CodeVerify(component, config.email, config.password)
            }

            is Config.CodeVerifyForgotPassword -> {
                val component = codeVerifyRPComponentFactory(
                    componentContext,
                    navigation::pop
                ) {
                    navigation.push(Config.ResetPassword(config.email))
                }
                AuthRootComponent.Child.CodeVerifyResetPassword(component, config.email)
            }

            is Config.EmailChoose -> {
                val component = emailChooseComponentFactory(
                    componentContext,
                    navigation::pop,
                ) { email ->
                    navigation.push(Config.CodeVerifyForgotPassword(email))
                }
                AuthRootComponent.Child.EmailChoose(component)
            }

            is Config.ResetPassword -> {
                val component = resetPasswordComponent(
                    componentContext,
                    navigation::pop
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
}