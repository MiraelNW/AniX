package com.miraelDev.vauma.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraelDev.vauma.navigation.authComponent.AuthRootComponent
import com.miraelDev.vauma.navigation.mainComponent.MainRootComponent
import com.miraelDev.vauma.navigation.navigationUi.NavId
import com.miraeldev.account.presentation.accountComponent.AccountComponent
import com.miraeldev.account.presentation.settings.editProfileScreen.EditProfileComponent.EditProfileComponent
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesComponent
import com.miraeldev.animelist.presentation.home.homeComponent.HomeComponent
import com.miraeldev.detailinfo.presentation.detailComponent.DetailComponent
import com.miraeldev.favourites.presentation.favouriteComponent.FavouriteComponent
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.EmailChooseComponent
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.ResetPasswordComponent
import com.miraeldev.navigation.decompose.authComponent.signUpComponent.SignUpComponent
import com.miraeldev.search.presentation.searchComponent.SearchAnimeComponent
import com.miraeldev.signin.presentation.signInComponent.SignInComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.CodeVerifyComponent
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.CodeVerifyRPComponent

interface AppRootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Auth(val component: AuthRootComponent) : Child
        data class Main(val component: MainRootComponent) : Child
    }
}