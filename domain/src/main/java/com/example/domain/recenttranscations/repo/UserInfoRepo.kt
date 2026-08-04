package com.example.domain.recenttranscations.repo

import com.example.domain.recenttranscations.model.UserInfo
import com.example.domain.recenttranscations.utils.Resource

interface UserInfoRepo {

    suspend fun getUserInfo(identifier: String): Resource<UserInfo>
}