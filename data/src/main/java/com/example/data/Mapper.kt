package com.example.data

import com.example.data.dto.RecentTransactionDto
import com.example.data.dto.UserInfoDto
import com.example.domain.recenttranscations.model.recenttransaction.RecentTransactionModelItem
import com.example.domain.recenttranscations.model.userinfo.UserInfoModel

fun RecentTransactionDto.toDomain(): RecentTransactionModelItem {
    return RecentTransactionModelItem(
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

fun UserInfoDto.toDomain(): UserInfoModel {
    return UserInfoModel(
        avatar = avatar,
        balance = balance,
        currency = currency,
        id = id,
        identifier = identifier,
        name = name
    )
}