package com.example.oneclickpay.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneclickpay.R

@Composable
fun TransactionsScreen() {

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        TransactionHeader()

        Spacer(modifier = Modifier.height(16.dp))
        TransactionItem(
            title = "apple store", date = "22-7-2026", time = "9:30 AM", amount = "1026506"
        )
        TransactionItem(
            title = "apple store", date = "22-7-2026", time = "9:30 AM", amount = "1026506"
        )
        TransactionItem(
            title = "apple store", date = "22-7-2026", time = "9:30 AM", amount = "1026506"
        )
    }

}


@Composable
fun TransactionItem(
    title: String,
    date: String,
    time: String,
    amount: String,
    icon: ImageVector = Icons.Outlined.ShoppingCart,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.background))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF23262B)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFC5D4FF),
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$date • $time",
                color = Color(0xFF9EA3AE),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Text(
            text = amount,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TransactionHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Transactions",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Review your recent activity across all accounts.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
        )
    }
}