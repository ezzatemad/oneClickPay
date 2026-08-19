package com.example.data

import com.example.data.db.entities.MyCardEntity
import com.example.data.db.entities.AllUsersEntity
import com.example.data.db.entities.TransactionEntity
import com.example.data.db.entities.UserInfoEntity
import com.example.data.dto.AllUserDto
import com.example.data.dto.RecentTransactionDto
import com.example.data.dto.UserInfoDto
import com.example.data.dto.SendMoneyRequestDto
import com.example.domain.recenttranscations.model.AddCard
import com.example.domain.recenttranscations.model.AllUsers
import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.model.UserInfo
import com.example.domain.recenttranscations.model.SendMoneyRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun AddCard.toEntity(id: String): MyCardEntity {
    return MyCardEntity(
        id = id,
        cardNumber = cardNumber,
        cardName = cardName,
        expiryDate = expiryDate,
        cvv = cvv
    )
}


fun MyCardEntity.toDomain(): AddCard {
    return AddCard(
        cardNumber = cardNumber,
        cardName = cardName,
        expiryDate = expiryDate,
        cvv = cvv,
    )
}

fun AllUserDto.toEntity(): AllUsersEntity {
    return AllUsersEntity(
        avatar = avatar,
        id = id,
        identifier = identifier,
        name = name
    )
}

fun AllUsersEntity.toDomain(): AllUsers {
    return AllUsers(
        avatar = avatar,
        id = id,
        identifier = identifier,
        name = name
    )
}

fun TransactionEntity.toDto(): SendMoneyRequestDto {
    return SendMoneyRequestDto(
        id = id,
        amount = amount,
        senderIdentifier = "",
        receiverIdentifier = "",
        currency = currency,
        title = description
    )
}

fun SendMoneyRequest.toEntity(id: String): TransactionEntity {
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

    return TransactionEntity(
        id = id,
        amount = amount,
        currency = currency,
        date = currentDate,
        time = currentTime,
        description = title,
        state = "PENDING",
        type = "SEND",
        isSynced = false
    )
}

fun SendMoneyRequest.toDto(id: String): SendMoneyRequestDto {
    return SendMoneyRequestDto(
        id = id,
        senderIdentifier = senderIdentifier,
        receiverIdentifier = receiverIdentifier,
        amount = amount,
        currency = currency,
        title = title
    )
}

fun RecentTransactionDto.toEntity(): TransactionEntity {

    return TransactionEntity(
        id = id.orEmpty(),
        amount = amount ?: 0.0,
        currency = currency.orEmpty(),
        date = date.orEmpty(),
        time = time.orEmpty(),
        description = description.orEmpty(),
        state = state ?: "COMPLETED",
        type = type ?: "SEND",
        isSynced = true
    )
}

fun TransactionEntity.toDomain(): RecentTransactionItem {
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

fun UserInfoDto.toEntity(): UserInfoEntity {
    return UserInfoEntity(
        id = id,
        avatar = avatar.orEmpty(),
        balance = balance,
        currency = currency,
        identifier = identifier,
        name = name
    )
}

fun UserInfoEntity.toDomain(): UserInfo {
    return UserInfo(
        id = id,
        avatar = avatar,
        balance = balance,
        currency = currency,
        identifier = identifier,
        name = name
    )
}
