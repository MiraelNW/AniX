package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import javax.inject.Inject

class SetPreferenceUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(key: String, value: Boolean) = repository.setPreference(key,value)
}