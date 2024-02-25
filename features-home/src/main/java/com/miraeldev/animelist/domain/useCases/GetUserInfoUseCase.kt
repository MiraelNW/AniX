package com.miraeldev.animelist.domain.useCases

import com.miraeldev.animelist.data.HomeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetUserInfoUseCase(private val homeRepository: HomeRepository) {

    operator fun invoke() = homeRepository.getUserInfo()

}