package com.example.data

import com.example.data.db.entities.RecentTransactionEntity
import com.example.data.dto.RecentTransactionDto
import com.example.data.dto.UserInfoDto
import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.model.UserInfo

// DTO -> Entity
fun RecentTransactionDto.toEntity(): RecentTransactionEntity {
    return RecentTransactionEntity(
        id = id,
        amount = amount,
        currency = currency,
        date = date,
        time = time,
        description = description,
        state = state,
        type = type
    )
}

// Entity -> Domain Model
fun RecentTransactionEntity.toDomain(): RecentTransactionItem {
    return RecentTransactionItem(
        id = id,
        amount = amount,
        currency = currency,
        date = date,
        time = time,
        description = description,
        state = state,
        type = type
    )
}
fun RecentTransactionDto.toDomain(): RecentTransactionItem {
    return RecentTransactionItem(
        amount = amount,
        currency = currency,
        date = date,
        description = description,
        id = id,
        state = state,
        time = time,
        type = type
    )
}

fun UserInfoDto.toDomain(): UserInfo {
    return UserInfo(
        avatar = avatar,
        balance = balance,
        currency = currency,
        id = id,
        identifier = identifier,
        name = name
    )
}