package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(
        currentPassword:String,
        newPassword:String,
        repeatedPassword:String
    ) = repository.changePassword(currentPassword, newPassword, repeatedPassword)
}