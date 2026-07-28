package com.example.data.apiservices

import com.example.data.dto.RecentTransactionDto
import com.example.data.dto.UserInfoDto
import com.example.domain.recenttranscations.model.userinfo.UserInfoModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class UserInfoApi(private val client: HttpClient) {

    suspend fun getUserInfoApi(identifier: String): UserInfoDto {

        return client.get("api/users/$identifier").body()
    }
}