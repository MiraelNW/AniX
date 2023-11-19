package com.miraeldev.signin.domain

import com.miraeldev.signin.data.repositories.SignInRepository
import javax.inject.Inject

class LogInWithGoogleUseCase @Inject constructor(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(idToken:String) = repository.logInWithGoogle(idToken)

}
