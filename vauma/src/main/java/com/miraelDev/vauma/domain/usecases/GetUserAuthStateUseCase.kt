package com.miraelDev.vauma.domain.usecases

import com.miraelDev.vauma.domain.repository.MainRepository
import javax.inject.Inject

class GetUserAuthStateUseCase @Inject constructor(private val repository: MainRepository) {
   operator fun invoke() = repository.getUserStatus()
}