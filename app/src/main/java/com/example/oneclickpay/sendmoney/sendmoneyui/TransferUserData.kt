package com.example.oneclickpay.sendmoney.sendmoneyui

data class TransferUser(
    val id: String,
    val name: String,
    val avatarUrl: String? = null
)

val sampleUsers = listOf(
    TransferUser("1", "Ahmed"),
    TransferUser("2", "Omar"),
    TransferUser("3", "Sara"),
    TransferUser("4", "Mona"),
    TransferUser("5", "Ali")
)