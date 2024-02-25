package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SetPreferenceUseCase(private val repository: AccountRepository) {
    suspend operator fun invoke(key: String, value: Boolean) = repository.setPreference(key,value)
}