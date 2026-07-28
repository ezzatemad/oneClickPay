package com.example.oneclickpay.home

import com.example.domain.recenttranscations.model.userinfo.UserInfoModel

sealed class UserInfoStates {

    data object Idle : UserInfoStates()
    data class Success(val userInfoModel: UserInfoModel) :
        UserInfoStates()

    data class Error(val errorMessage: String) : UserInfoStates()
}