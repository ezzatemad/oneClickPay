package com.example.oneclickpay.card.addnewcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.recenttranscations.model.AddCard
import com.example.domain.recenttranscations.usecase.mycards.AddCardUseCase
import com.example.domain.recenttranscations.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel(
    private val addCardUseCase: AddCardUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CardsState>(CardsState.Idle)
    val state: StateFlow<CardsState> = _state.asStateFlow()


    fun processIntent(intent: CardsIntent) {
        when (intent) {
            is CardsIntent.AddCard -> {
                addCard(intent.addCardRequest)
            }
        }
    }

    private fun addCard(addCardRequest: AddCard) {
        viewModelScope.launch {
            _state.value = CardsState.Loading
            when (val result = addCardUseCase(addCardRequest)) {
                is Resource.Success -> _state.value = CardsState.Success(addCardRequest)
                is Resource.Error -> _state.value = CardsState.Error(result.message)
                is Resource.Loading -> _state.value = CardsState.Loading
            }
        }
    }
}