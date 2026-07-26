package com.example.oneclickpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneclickpay.dashboard.DashBoardScreen
import com.example.oneclickpay.ui.theme.OneClickPayTheme

class DashBoardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneClickPayTheme {
                HomeActivity()
            }
        }
    }
}

@Composable
fun HomeActivity() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val borderColor = colorResource(R.color.gary_white)
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(R.color.background),
                modifier = Modifier.drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            ) {
                val navColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.selected_icon),
                    selectedTextColor = colorResource(R.color.selected_item),
                    unselectedIconColor = colorResource(R.color.text_card),
                    unselectedTextColor = colorResource(R.color.text_card),
                    indicatorColor = colorResource(R.color.selected_item)
                )
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.home_ic),
                            contentDescription = stringResource(R.string.home)
                        )
                    },
                    label = { Text(text = stringResource(R.string.home)) },
                    colors = navColors
                )

                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.card_ic),
                            contentDescription = stringResource(R.string.cards)
                        )
                    },
                    label = { Text(text = stringResource(R.string.cards)) },
                    colors = navColors
                )

                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.transaction_ic),
                            contentDescription = stringResource(R.string.transactions)
                        )
                    },
                    label = { Text(text = stringResource(R.string.transactions)) },
                    colors = navColors
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedIndex) {
                0 -> DashBoardScreen()
            }
        }
    }
}