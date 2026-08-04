package com.example.oneclickpay.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composereview.mainascreen.Constants
import com.example.domain.recenttranscations.usecase.UserInfoUseCase
import com.example.domain.recenttranscations.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserInfoViewModel(private val userInfoUseCase: UserInfoUseCase) :
    ViewModel() {

    private val _uiStates = MutableStateFlow<UserInfoStates>(UserInfoStates.Idle)
    var uiStates: StateFlow<UserInfoStates> = _uiStates.asStateFlow()


    init {
        processIntent(UserInfoIntent.LoadUserProfile(Constants.PHONE_IDENTIFIER))
    }

    fun processIntent(intent: UserInfoIntent) {

        when (intent) {
            is UserInfoIntent.LoadUserProfile -> {
                fetchUserInfo(intent.phoneNumber)
            }
        }
    }

    private fun fetchUserInfo(phoneNumber: String) {
        viewModelScope.launch {
            _uiStates.value = UserInfoStates.Loading
            when (val result = userInfoUseCase(phoneNumber)) {
                is Resource.Success -> {
                    _uiStates.value = UserInfoStates.Success(result.data)
                }

                is Resource.Error -> {
                    _uiStates.value = UserInfoStates.Error(result.message)
                }

                is Resource.Loading -> {
                    _uiStates.value = UserInfoStates.Loading
                }
            }
        }
    }
}