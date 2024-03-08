package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SetIsWifiOnlyUseCase(private val repository: AccountRepository) {
    suspend operator fun invoke(isWifi: Boolean) = repository.setIsWifiOnly(isWifi)
}