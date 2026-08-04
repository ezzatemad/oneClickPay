package com.example.oneclickpay.home

import com.example.domain.recenttranscations.model.UserInfo

sealed class UserInfoStates {

    data object Idle : UserInfoStates()
    data object Loading : UserInfoStates()
    data class Success(val userInfoModel: UserInfo) :
        UserInfoStates()

    data class Error(val errorMessage: String) : UserInfoStates()
}