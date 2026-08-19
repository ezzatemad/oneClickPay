package com.example.oneclickpay.card.allcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.recenttranscations.usecase.mycards.MyCardsUseCase
import com.example.domain.recenttranscations.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyCardsViewModel(
    private val myCardsUseCase: MyCardsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MyCardsState>(MyCardsState.Idle)
    val state: StateFlow<MyCardsState> = _state.asStateFlow()

    fun processIntent(intent: MyCardsIntent) {
        when (intent) {
            is MyCardsIntent.DisplayMyCards -> {
                DisplayMyCards()
            }
        }
    }

    private fun DisplayMyCards() {
        viewModelScope.launch {
            when (val result = myCardsUseCase()) {
                is Resource.Success -> _state.value = MyCardsState.Success(result.data)
                is Resource.Error -> _state.value = MyCardsState.Error(result.message)
                else -> {}
            }
        }
    }
}