package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: AccountRepository) {
    operator fun invoke() = repository.getUserInfo()
}