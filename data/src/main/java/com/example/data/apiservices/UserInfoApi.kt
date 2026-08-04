package com.example.data.apiservices

import com.example.data.dto.UserInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserInfoApi(private val client: HttpClient) {

    suspend fun getUserInfoApi(identifier: String): UserInfoDto {

        return client.get("api/users/$identifier").body()
    }
}