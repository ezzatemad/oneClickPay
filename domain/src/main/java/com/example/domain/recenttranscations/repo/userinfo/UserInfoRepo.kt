package com.example.domain.recenttranscations.repo.userinfo

import com.example.domain.recenttranscations.model.userinfo.UserInfoModel


interface UserInfoRepo {

    suspend fun getUserInfo(identifier: String): UserInfoModel
}