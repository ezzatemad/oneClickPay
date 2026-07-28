package com.example.oneclickpay.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composereview.mainascreen.Constants
import com.example.domain.recenttranscations.usecase.RecentTransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashBoardScreenViewModel(private val recentTransactionUseCase: RecentTransactionUseCase) :
    ViewModel() {

    private val _uiStates = MutableStateFlow<DashBoardScreenStates>(DashBoardScreenStates.Idle)
    var uiStates: StateFlow<DashBoardScreenStates> = _uiStates.asStateFlow()


    init {
        processIntent(DashBoardScreenIntent.LoadTransactions(Constants.PHONE_IDENDIFER))
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
            _uiStates.value = DashBoardScreenStates.Loading
            try {
                val transactionList =
                    recentTransactionUseCase.getAllTransactionsUseCase(phoneNumber)
                _uiStates.value = DashBoardScreenStates.Success(transactionList)
            } catch (e: Exception) {
                _uiStates.value =
                    DashBoardScreenStates.Error(e.localizedMessage ?: "حدث خطأ غير متوقع")
            }
        }
    }
}