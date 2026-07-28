package com.example.oneclickpay.home



sealed class UserInfoIntent {

    data class LoadUserProfile(val phoneNumber: String) : UserInfoIntent()
}