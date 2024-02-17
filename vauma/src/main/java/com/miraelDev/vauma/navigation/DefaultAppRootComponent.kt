package com.miraelDev.vauma.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.miraelDev.vauma.navigation.authComponent.DefaultAuthRootComponent
import com.miraelDev.vauma.navigation.mainComponent.DefaultMainRootComponent
import com.miraelDev.vauma.navigation.navigationUi.NavId
import com.miraeldev.account.presentation.accountComponent.DefaultAccountComponent
import com.miraeldev.account.presentation.settings.editProfileScreen.EditProfileComponent.DefaultEditProfileComponent
import com.miraeldev.animelist.presentation.categories.categoriesComponent.DefaultCategoriesComponent
import com.miraeldev.animelist.presentation.home.homeComponent.DefaultHomeComponent
import com.miraeldev.detailinfo.presentation.detailComponent.DefaultDetailComponent
import com.miraeldev.favourites.presentation.favouriteComponent.DefaultFavouriteComponent
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.DefaultEmailChooseComponent
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.DefaultResetPasswordComponent
import com.miraeldev.search.presentation.searchComponent.DefaultSearchAnimeComponent
import com.miraeldev.signin.presentation.signInComponent.DefaultSignInComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.DefaultCodeVerifyComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.DefaultCodeVerifyRPComponent
import com.miraeldev.signup.presentation.signUpScreen.signUpComponent.DefaultSignUpComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultAppRootComponent @AssistedInject constructor(
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("isUserAuthorized") private val isUserAuthorized: Boolean,
    @Assisted("onDarkThemeClick") private val onDarkThemeClick: () -> Unit,
    private val mainRootComponent: DefaultMainRootComponent.Factory,
    private val authRootComponent: DefaultAuthRootComponent.Factory
) : AppRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AppRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = if (isUserAuthorized) Config.Main else Config.Auth,
        key = "AppRootStack",
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): AppRootComponent.Child {
        return when (config) {
            is Config.Auth -> {
                val component = authRootComponent.create(
                    componentContext = componentContext,
                    logIn = { navigation.replaceAll(Config.Main) }
                )
                AppRootComponent.Child.Auth(component)
            }

            is Config.Main -> {
                val component = mainRootComponent.create(
                    componentContext = componentContext,
                    onDarkThemeClick = onDarkThemeClick,
                    onLogOutComplete = { navigation.replaceAll(Config.Auth) }
                )
                AppRootComponent.Child.Main(component)
            }
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config

        @Serializable
        data object Main : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("isUserAuthorized") isUserAuthorized: Boolean,
            @Assisted("onDarkThemeClick") onDarkThemeClick: () -> Unit,
        ): DefaultAppRootComponent
    }
}

