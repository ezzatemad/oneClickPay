package com.example.domain.recenttranscations.usecase

import com.example.domain.recenttranscations.model.UserInfo
import com.example.domain.recenttranscations.repo.UserInfoRepo
import com.example.domain.recenttranscations.utils.Resource

class UserInfoUseCase(
    private val userInfoRepo: UserInfoRepo
) {

    suspend operator fun invoke(identifier: String): Resource<UserInfo> {
        return userInfoRepo.getUserInfo(identifier)
    }

}