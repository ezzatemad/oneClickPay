package com.example.oneclickpay.sendmoney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.recenttranscations.model.AllUsers
import com.example.domain.recenttranscations.model.SendMoneyRequest
import com.example.domain.recenttranscations.usecase.AllUserUseCase
import com.example.domain.recenttranscations.usecase.SendMoneyUseCase
import com.example.domain.recenttranscations.utils.Resource
import com.example.oneclickpay.sendmoney.SendMoneyState.getAllUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SendMoneyViewModel(
    private val sendMoneyUseCase: SendMoneyUseCase,
    private val getAllUserUseCase: AllUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SendMoneyState>(SendMoneyState.Idle)
    val state: StateFlow<SendMoneyState> = _state.asStateFlow()

    fun processIntent(intent: SendMoneyIntent) {
        when (intent) {
            is SendMoneyIntent.ConfirmTransfer -> {
                performTransfer(intent.sendMoneyRequest)
            }

            is SendMoneyIntent.getAllUser -> {
                getAllUsers()
            }
        }
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            _state.value = SendMoneyState.Loading

            when (val result = getAllUserUseCase()) {
                is Resource.Success<*> -> {
                    val users = result.data
                    _state.value = getAllUser(users as List<AllUsers>)
                }

                is Resource.Error -> {
                    _state.value =
                        SendMoneyState.Error(result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    _state.value = SendMoneyState.Loading
                }
            }
        }
    }

    private fun performTransfer(sendMoneyRequest: SendMoneyRequest) {
        viewModelScope.launch {
            _state.value = SendMoneyState.Loading

            when (val result = sendMoneyUseCase(sendMoneyRequest)) {
                is Resource.Success -> _state.value = SendMoneyState.Success("تم التحويل بنجاح!")
                is Resource.Error -> _state.value = SendMoneyState.Error(result.message)
                is Resource.Loading -> _state.value = SendMoneyState.Loading
            }
        }
    }

//    // دالة لإعادة الـ State إلى Idle لتفادي إعادة تنشيط الأحداث عند الـ Recomposition
//    fun resetState() {
//        _state.value = SendMoneyState.Idle
//    }
}