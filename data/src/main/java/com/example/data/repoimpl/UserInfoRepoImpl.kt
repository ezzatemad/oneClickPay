package com.example.data.repoimpl

import com.example.data.apiservices.UserInfoApi

import com.example.data.toDomain
import com.example.domain.recenttranscations.model.userinfo.UserInfoModel
import com.example.domain.recenttranscations.repo.userinfo.UserInfoRepo

class UserInfoRepoImpl(private val userInfoApi: UserInfoApi) :
    UserInfoRepo {

    override suspend fun getUserInfo(identifier: String): UserInfoModel {
        val response = userInfoApi.getUserInfoApi(identifier)

        return response.toDomain()
    }


}