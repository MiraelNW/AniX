package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke() = repository.logOutUser()
}