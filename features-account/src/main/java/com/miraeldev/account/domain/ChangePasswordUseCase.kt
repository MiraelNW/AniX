package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import me.tatarka.inject.annotations.Inject

@Inject
class ChangePasswordUseCase(private val repository: AccountRepository) {
    suspend operator fun invoke(
        currentPassword:String,
        newPassword:String,
        repeatedPassword:String
    ) = repository.changePassword(currentPassword, newPassword, repeatedPassword)
}