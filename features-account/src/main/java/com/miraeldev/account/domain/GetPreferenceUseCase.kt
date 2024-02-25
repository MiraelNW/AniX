package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetPreferenceUseCase(private val repository: AccountRepository) {
    operator fun invoke(key:String) = repository.getPreference(key)
}