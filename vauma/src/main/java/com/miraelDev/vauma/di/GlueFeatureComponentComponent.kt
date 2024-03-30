package com.miraelDev.vauma.di

import com.miraelDev.vauma.glue.account.di.AccountFeatureComponent
import com.miraelDev.vauma.glue.detailInfo.di.AnimeDetailInfoFeatureComponent
import com.miraelDev.vauma.glue.favourites.di.FavouriteFeatureComponent
import com.miraelDev.vauma.glue.forgotPassword.di.ForgotPasswordFeatureComponent
import com.miraelDev.vauma.glue.home.di.HomeFeatureComponent
import com.miraelDev.vauma.glue.search.di.SearchFeatureComponent
import com.miraelDev.vauma.glue.signIn.di.SignInFeatureComponent
import com.miraelDev.vauma.glue.signUp.di.SignUpFeatureComponent
import com.miraelDev.vauma.glue.video.di.VideoPlayerFeatureComponent
import com.miraeldev.di.DataComponent
import com.miraeldev.imageloader.di.ImageLoaderComponent

interface GlueFeatureComponentComponent :
    StoreFactoryComponent,
    DataComponent,
    HomeFeatureComponent,
    SearchFeatureComponent,
    FavouriteFeatureComponent,
    AccountFeatureComponent,
    VideoPlayerFeatureComponent,
    AnimeDetailInfoFeatureComponent,
    ForgotPasswordFeatureComponent,
    SignInFeatureComponent,
    SignUpFeatureComponent,
    MainFeatureComponent,
    ImageLoaderComponent,
    LoggerComponent