package com.example.domain.recenttranscations.usecase

import com.example.domain.recenttranscations.model.userinfo.UserInfoModel
import com.example.domain.recenttranscations.repo.userinfo.UserInfoRepo

class UserInfoUseCase(
    val userInfoRepo: UserInfoRepo
) {

    suspend fun getUserInfoUseCase(identifier: String): UserInfoModel {

        return userInfoRepo.getUserInfo(identifier)
    }

}