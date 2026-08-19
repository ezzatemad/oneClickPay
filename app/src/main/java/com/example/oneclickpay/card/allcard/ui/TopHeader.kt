package com.example.oneclickpay.card.allcard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneclickpay.R


@Composable
fun TopHeader(cardCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Active Assets",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(R.color.info_color))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "$cardCount Cards",
                color = colorResource(R.color.button_color),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}