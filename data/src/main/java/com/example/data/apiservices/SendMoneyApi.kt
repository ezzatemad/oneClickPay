package com.example.data.apiservices

import com.example.data.dto.RecentTransactionDto
import com.example.data.dto.SendMoneyRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SendMoneyApi(private val client: HttpClient) {

    suspend fun sendMoney(request: SendMoneyRequestDto): RecentTransactionDto {
        return client.post("api/transactions/send") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}