package com.example.data.apiservices

import com.example.data.dto.AllUserDto
import com.example.domain.recenttranscations.model.AllUsers
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class AllUsersApi(private val client: HttpClient) {
    suspend fun getAllUsers(): List<AllUserDto> {
        return client.get("api/users").body()
    }
}