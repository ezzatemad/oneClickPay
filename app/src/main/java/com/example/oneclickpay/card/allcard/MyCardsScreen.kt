package com.example.oneclickpay.card.allcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.recenttranscations.model.AddCard
import com.example.oneclickpay.R
import com.example.oneclickpay.card.addnewcard.cardui.CardImage
import com.example.oneclickpay.card.allcard.MyCardsState.Success
import com.example.oneclickpay.card.allcard.ui.BottomSection
import com.example.oneclickpay.card.allcard.ui.TopHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyCardsScreen(
    onAddNewCardClick: () -> Unit = {},
    viewModel: MyCardsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.processIntent(MyCardsIntent.DisplayMyCards)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(16.dp)
    ) {
        val cards = (state as? Success)?.allMyCards ?: emptyList()
        TopHeader(cardCount = cards.size)

        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.weight(1f)) {
            when (val state = state) {

                is MyCardsState.Success -> {
                    if (state.allMyCards.isEmpty()) {
                        Text(
                            text = "No cards found. Add your first card!",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        CardsListSection(
                            cards = state.allMyCards,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                is MyCardsState.Error -> {
                    Text(
                        text = state.errorMessage,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {}
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BottomSection(onAddNewCardClick = onAddNewCardClick)
    }
}

@Composable
fun CardsListSection(
    cards: List<AddCard>,
    modifier: Modifier = Modifier
) {
    if (cards.isEmpty()) return

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = cards,
            key = { card -> card.cardNumber }
        ) { card ->
            CardImage(
                cardNumber = card.cardNumber,
                cardHolderName = card.cardName,
                expiryDate = card.expiryDate
            )
        }
    }
}

