package com.example.oneclickpay.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composereview.mainascreen.Constants
import com.example.domain.recenttranscations.usecase.RecentTransactionUseCase
import com.example.domain.recenttranscations.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashBoardScreenViewModel(private val recentTransactionUseCase: RecentTransactionUseCase) :
    ViewModel() {

    private val _uiStates = MutableStateFlow<DashBoardScreenStates>(DashBoardScreenStates.Idle)
    var uiStates: StateFlow<DashBoardScreenStates> = _uiStates.asStateFlow()


    init {
        processIntent(DashBoardScreenIntent.LoadTransactions(Constants.PHONE_IDENTIFIER))
    }

    fun processIntent(intent: DashBoardScreenIntent) {

        when (intent) {
            is DashBoardScreenIntent.LoadTransactions -> {
                fetchTransactions(intent.phoneNumber)
            }
        }
    }

    private fun fetchTransactions(phoneNumber: String) {
        viewModelScope.launch {
            // الاستماع للـ Flow التفاعلي المبعوث من UseCase / Repository
            recentTransactionUseCase(phoneNumber).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiStates.value = DashBoardScreenStates.Success(result.data)
                    }

                    is Resource.Error -> {
                        _uiStates.value = DashBoardScreenStates.Error(
                            result.message ?: "Unknown error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _uiStates.value = DashBoardScreenStates.Loading
                    }
                }
            }
        }
    }
}
