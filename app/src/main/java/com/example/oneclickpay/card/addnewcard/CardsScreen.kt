package com.example.oneclickpay.card.addnewcard

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.recenttranscations.model.AddCard
import com.example.oneclickpay.R
import com.example.oneclickpay.card.addnewcard.cardui.AddCardButton
import com.example.oneclickpay.card.addnewcard.cardui.CardEditText
import com.example.oneclickpay.card.addnewcard.cardui.CardImage
import com.example.oneclickpay.card.addnewcard.cardui.CardsHeader
import com.example.oneclickpay.card.addnewcard.cardui.InformationBox
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardsScreen(
    viewModel: CardViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    var cardNumber by rememberSaveable { mutableStateOf("") }
    var cardHolderName by rememberSaveable { mutableStateOf("") }
    var expiryDate by rememberSaveable { mutableStateOf("") }
    var cvv by rememberSaveable { mutableStateOf("") }

    var cardNumberError by rememberSaveable { mutableStateOf<String?>(null) }
    var cardNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var expiryDateError by rememberSaveable { mutableStateOf<String?>(null) }
    var cvvError by rememberSaveable { mutableStateOf<String?>(null) }


    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (val current = uiState) {
            is CardsState.Success -> {
                Toast.makeText(context, "Card added successfully!", Toast.LENGTH_SHORT).show()
                cardNumber = ""
                cardHolderName = ""
                expiryDate = ""
                cvv = ""
                cardNumberError = null
                cardNameError = null
                expiryDateError = null
                cvvError = null
            }

            is CardsState.Error -> {
                Toast.makeText(context, current.errorMassage, Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.background))
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CardsHeader()

        Spacer(modifier = Modifier.height(4.dp))
        CardImage(
            cardNumber = cardNumber,
            cardHolderName = cardHolderName,
            expiryDate = expiryDate
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Card Number",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        CardEditText(
            value = cardNumber,
            onValueChange = { input ->
                if (input.length <= 16) {
                    cardNumber = input.filter { it.isDigit() }
                    if (cardNumberError != null) cardNumberError = null
                }
            },
            hint = "0000 0000 0000 0000",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = cardNumberError != null,
            errorMessage = cardNumberError,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Card Holder Name",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        CardEditText(
            value = cardHolderName,
            onValueChange = { input ->
                cardHolderName = input
                if (cardNameError != null) cardNameError = null
            },
            hint = "Enter Full Name",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = cardNameError != null,
            errorMessage = cardNameError,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Expiry Date",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                CardEditText(
                    value = expiryDate,
                    onValueChange = { input ->
                        val cleanInput = input.filter { it.isDigit() }
                        expiryDate = when {
                            cleanInput.length <= 2 -> cleanInput
                            cleanInput.length <= 4 -> "${cleanInput.take(2)}/${cleanInput.drop(2)}"
                            else -> expiryDate
                        }
                        if (expiryDateError != null) expiryDateError = null
                    },
                    hint = "MM/YY",
                    isError = expiryDateError != null,
                    errorMessage = expiryDateError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "CVV",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                CardEditText(
                    value = cvv,
                    onValueChange = { input ->
                        if (input.length <= 3) {
                            cvv = input.filter { char -> char.isDigit() }
                            if (cvvError != null) cvvError = null
                        }
                    },
                    hint = "•••",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    isError = cvvError != null,
                    errorMessage = cvvError,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        InformationBox()

        AddCardButton(
            onClick = {
                val cleanCardNumber = cardNumber.trim()
                val cleanName = cardHolderName.trim()
                val cleanExpiry = expiryDate.trim()
                val cleanCvv = cvv.trim()

                cardNumberError = null
                cardNameError = null
                expiryDateError = null
                cvvError = null

                var isValid = true

                if (cleanCardNumber.length != 16) {
                    cardNumberError = "Card number must be 16 digits"
                    isValid = false
                }

                if (cleanName.length < 3) {
                    cardNameError = "Name must be at least 3 characters"
                    isValid = false
                }

                if (!isValidExpiryDate(cleanExpiry)) {
                    expiryDateError = "Enter a valid date (MM/YY)"
                    isValid = false
                }

                if (cleanCvv.length != 3) {
                    cvvError = "CVV must be 3 digits"
                    isValid = false
                }

                if (isValid) {
                    viewModel.processIntent(
                        CardsIntent.AddCard(
                            AddCard(
                                cardNumber = cleanCardNumber,
                                cardName = cleanName,
                                expiryDate = cleanExpiry,
                                cvv = cleanCvv
                            )
                        )
                    )
                }
            }
        )
    }
}

private fun isValidExpiryDate(expiry: String): Boolean {
    val regex = Regex("^(0[1-9]|1[0-2])/([0-9]{2})\$")
    if (!regex.matches(expiry)) return false

    val parts = expiry.split("/")
    val month = parts[0].toIntOrNull() ?: return false
    val year = parts[1].toIntOrNull() ?: return false

    return month in 1..12 && year >= 26
}