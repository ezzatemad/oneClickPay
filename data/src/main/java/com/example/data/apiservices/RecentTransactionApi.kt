package com.example.data.apiservices

import com.example.data.dto.RecentTransactionDto
import com.example.domain.recenttranscations.model.recenttransaction.RecentTransactionModelItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RecentTransactionApi(private val client: HttpClient) {

    suspend fun getAllRecentTransactionApi(identifier: String): List<RecentTransactionDto> {

        return client.get("api/transactions/$identifier").body()
    }
}