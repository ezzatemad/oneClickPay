package com.example.data.repoimpl

import com.example.data.apiservices.UserInfoApi
import com.example.data.db.dao.UserInfoDao
import com.example.data.toEntity
import com.example.domain.recenttranscations.model.UserInfo
import com.example.domain.recenttranscations.repo.UserInfoRepo
import com.example.domain.recenttranscations.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException

class UserInfoRepoImpl(private val userInfoApi: UserInfoApi, private val userInfoDao: UserInfoDao) :
    UserInfoRepo {

    override suspend fun getUserInfo(identifier: String): Resource<UserInfo> {

        return try {
            val remoteResponse = userInfoApi.getUserInfoApi(identifier)
            val entities = remoteResponse.toEntity()

            userInfoDao.clearUserInfo()
            userInfoDao.insertUserInfo(entities)

            val localData = userInfoDao.getUserInfo()
            Resource.Success(localData)
        } catch (e: Exception) {
            val localData = userInfoDao.getUserInfo()

            if (localData != null) {
                Resource.Success(localData)
            } else {
                val errorMessage = when (e) {
                    is ClientRequestException -> "Error in order data: ${e.response.status.value}"
                    is ServerResponseException -> "Server error: ${e.response.status.value}"
                    else -> "No internet connection and no cached data available."
                }
                Resource.Error(errorMessage, e)
            }
        }
    }
}
