package com.example.oneclickpay.card.addnewcard.cardui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneclickpay.R


@Composable
fun CardsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Card",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Link a secure payment method to your digital vault.",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun CardImage(
    cardNumber: String,
    cardHolderName: String,
    expiryDate: String
) {
    val cardType = getCardType(cardNumber)

    val cardGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF11151F),
            Color(0xFF202738)
        )
    )
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = cardGradient, shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = cardType.title,
                    fontSize = 14.sp,
                    color = colorResource(R.color.white)
                )
                Icon(
                    painter = painterResource(R.drawable.wifi_ic),
                    contentDescription = "",
                    tint = colorResource(R.color.wifi_color)
                )
            }
            Icon(
                painter = painterResource(R.drawable.visa_ic),
                contentDescription = "",
                modifier = Modifier.padding(bottom = 4.dp),
                tint = Color.Unspecified
            )

            Text(
                text = cardNumber.ifBlank { "•••• •••• •••• ••••" },
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "CARD HOLDER",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Text(
                    text = "EXPIRES",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = cardHolderName.ifEmpty { "YOUR NAME" },
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
                Text(
                    text = expiryDate.ifEmpty { "MM/YY" },
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
            }
        }
    }
}


enum class CardType(val title: String) {
    VISA("VISA"),
    MASTERCARD("MASTERCARD"),
    AMEX("AMERICAN EXPRESS"),
    MEEZA("MEEZA"),
    UNKNOWN("CARD")
}
fun getCardType(cardNumber: String): CardType {
    val cleanNumber = cardNumber.replace(" ", "")
    return when {
        //start with 4
        cleanNumber.startsWith("4") -> CardType.VISA
        //start with 51 to 55
        cleanNumber.matches(Regex("^(5[1-5]|2[2-7]).*")) -> CardType.MASTERCARD
        //strat with 34 or 37
        cleanNumber.matches(Regex("^3[47].*")) -> CardType.AMEX
        //start with 5078
        cleanNumber.startsWith("5078") -> CardType.MEEZA
        else -> CardType.UNKNOWN
    }
}