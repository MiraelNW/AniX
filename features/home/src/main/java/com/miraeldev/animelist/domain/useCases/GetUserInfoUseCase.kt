package com.miraeldev.animelist.domain.useCases

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.animelist.data.HomeRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    operator fun invoke() = homeRepository.getUserInfo()

}