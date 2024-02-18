package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(private val repository: AccountRepository) {
    operator fun invoke(key:String) = repository.getPreference(key)
}