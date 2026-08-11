package com.example.oneclickpay.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.oneclickpay.R
import com.example.oneclickpay.home.UserInfoStates
import com.example.oneclickpay.home.UserInfoViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashBoardScreen(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {},
    viewModel: DashBoardScreenViewModel = koinViewModel(),
    userInfoViewModel: UserInfoViewModel = koinViewModel(),
    onClick: () -> Unit = {}
) {
    MonitorWorkStatus(context = LocalContext.current)

    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()
    val userInfoState by userInfoViewModel.uiStates.collectAsStateWithLifecycle()

    BackHandler(enabled = isExpanded) {
        onExpandedChange(false)
    }

    val totalBalance = when (val state = userInfoState) {
        is UserInfoStates.Success -> state.userInfoModel.balance.toString()
        else -> null
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
    ) {
        if (!isExpanded) {
            BalanceCard(balance = totalBalance)
            SendButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp),
                onClick = { onClick() }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Transactions",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_card),
                    modifier = Modifier.padding(start = 8.dp)
                )

                Text(
                    text = "See All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.see_color),
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable { onExpandedChange(true) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (val state = uiState) {
                is DashBoardScreenStates.Idle -> {}

                is DashBoardScreenStates.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                is DashBoardScreenStates.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }

                is DashBoardScreenStates.Success -> {
                    val itemHeightDp = 76.dp
                    val maxFitCount = (maxHeight / itemHeightDp).toInt().coerceAtLeast(1)

                    val transactions = if (isExpanded) {
                        state.recentTransaction
                    } else {
                        state.recentTransaction.take(maxFitCount)
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        userScrollEnabled = isExpanded,
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(
                            items = transactions,
                            key = { it.id }
                        ) { transaction ->
                            TransactionCard(transaction = transaction)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SendButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.balanced_card_color)),
        border = BorderStroke(
            1.5.dp,
            color = colorResource(R.color.gary_white)
        ),
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .fillMaxWidth(0.5f)
            .clip(RoundedCornerShape(32.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.send_ic),
                contentDescription = "Send Icon",
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Send",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.text_card)
            )
        }
    }
}

@Composable
fun BalanceCard(
    balance: String?,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.balanced_card_color),
            contentColor = Color.White
        ),
        border = BorderStroke(
            width = 1.5.dp,
            color = colorResource(R.color.gary_white)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total Balance",
                color = colorResource(R.color.text_card),
                fontSize = 16.sp
            )

            if (balance != null) {
                Text(
                    text = balance,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp)
                )
            } else {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(24.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Composable
fun TransactionCard(transaction: RecentTransactionItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.balanced_card_color)),
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.gary_white)
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.transaction_ic),
                contentDescription = "Transaction Icon",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = colorResource(R.color.image_background).copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = transaction.description,
                        color = colorResource(R.color.text_card),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = "${transaction.amount}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${transaction.date} • ",
                            fontSize = 11.sp,
                            color = colorResource(R.color.text_card)
                        )

                        Text(
                            text = transaction.currency,
                            fontSize = 11.sp,
                            color = colorResource(R.color.text_card),
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.icon),
                            contentDescription = "Category Icon",
                            tint = colorResource(R.color.text_card),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = transaction.state,
                        fontSize = 10.sp,
                        color = colorResource(R.color.text_card)
                    )
                }
            }
        }
    }
}