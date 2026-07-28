package com.example.oneclickpay.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composereview.mainascreen.Constants
import com.example.domain.recenttranscations.usecase.RecentTransactionUseCase
import com.example.domain.recenttranscations.usecase.UserInfoUseCase
import com.example.oneclickpay.dashboard.DashBoardScreenIntent
import com.example.oneclickpay.dashboard.DashBoardScreenStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserInfoViewModel(private val userInfoUseCase: UserInfoUseCase) :
    ViewModel() {

    private val _uiStates = MutableStateFlow<UserInfoStates>(UserInfoStates.Idle)
    var uiStates: StateFlow<UserInfoStates> = _uiStates.asStateFlow()


    init {
        processIntent(UserInfoIntent.LoadUserProfile(Constants.PHONE_IDENDIFER))
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
            try {
                val userInfo = userInfoUseCase.getUserInfoUseCase(phoneNumber)
                _uiStates.value = UserInfoStates.Success(userInfo)
            } catch (e: Exception) {
                _uiStates.value =
                    UserInfoStates.Error(e.localizedMessage ?: "حدث خطأ أثناء تحميل البيانات")
            }

        }
    }
}