package com.example.oneclickpay.card.addnewcard.cardui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneclickpay.R

@Composable
fun AddCardButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        enabled = true,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.button_color),
            contentColor = colorResource(R.color.button_text_color)
        ),
        elevation = ButtonDefaults.buttonElevation(12.dp)
    ) {
        Text(
            text = "Add Card",
            fontSize = 22.sp,
            color = colorResource(R.color.button_text_color),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun InformationBox() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.info_color))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(height = 40.dp, width = 38.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.lock_ic),
                    contentDescription = "Lock Icon",
                    tint = colorResource(R.color.button_color),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Your payment information is encrypted\n" +
                        "and never stored on our servers.\n" +
                        "Transactions are processed through an\n" +
                        "industry-standard secure gateway.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
