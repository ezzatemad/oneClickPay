package com.example.oneclickpay.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneclickpay.R


@Composable
fun DashBoardScreen() {
    var isExpanded by remember { mutableStateOf(false) }

    BackHandler(enabled = isExpanded) {
        isExpanded = false
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        if (isExpanded) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "← Back",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { isExpanded = false }
                            .padding(8.dp)
                    )
                    Text(
                        text = "All Transactions",
                        color = colorResource(R.color.text_card),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        } else {
            item {
                BalanceCard()
            }

            item {
                SendButton(modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.text_card),
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Text(
                        text = "See All",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.see_color),
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { isExpanded = true } // Hides rest & shows 50 cards
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        val itemCounter = if (isExpanded) 50 else 3

        items(count = itemCounter) {
            TransactionCard()
        }
    }
}
//@Composable
//fun DashBoardScreen() {
//
//    var isExpanded by remember { mutableStateOf(false) }
//
//    BackHandler(enabled = isExpanded) {
//        isExpanded = false
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = colorResource(R.color.background)),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        BalanceCard()
//
//        SendButton()
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Recent Transactions",
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold,
//                color = colorResource(R.color.text_card),
//                modifier = Modifier.padding(start = 8.dp)
//            )
//
//            Text(
//                text = "See All",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Normal,
//                color = colorResource(R.color.see_color),
//                modifier = Modifier.padding(end = 8.dp)
//            )
//
//
//        }
//        TransactionCard()
//
//    }
//}

@Composable
fun SendButton(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.balanced_card_color)),
        border = BorderStroke(
            2.dp,
            color = colorResource(R.color.gary_white)
        ),
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth(.5f),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.send_ic),
                contentDescription = "Send Icon",
                tint = Color.White,
                modifier = Modifier.padding(end = 12.dp)
            )
            Text(text = "Send", fontSize = 16.sp, color = colorResource(R.color.text_card))
        }
    }
}

@Composable
fun BalanceCard(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.balanced_card_color)
        ),
        border = BorderStroke(
            2.dp,
            color = colorResource(R.color.gary_white)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.balanced_card_color))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Balance",
                color = colorResource(R.color.text_card),
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp, top = 12.dp)
            )


            Text(
                text = "$24,354,651",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun TransactionCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.balanced_card_color)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        border = BorderStroke(
            width = 2.dp, color = colorResource(R.color.gary_white)
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.trans_ic),
                contentDescription = "Transaction Icon",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = colorResource(R.color.image_background).copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Apple Store",
                        color = colorResource(R.color.text_card),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "-1,299.00",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "2:45 PM • ",
                            fontSize = 12.sp,
                            color = colorResource(R.color.text_card)
                        )

                        Text(
                            text = "Electronics",
                            fontSize = 12.sp,
                            color = colorResource(R.color.text_card),
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.icon),
                            contentDescription = "Category Icon",
                            tint = colorResource(R.color.text_card),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "COMPLETED",
                        fontSize = 10.sp,
                        color = colorResource(R.color.text_card)
                    )
                }
            }
        }
    }
}
