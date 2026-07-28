package com.example.oneclickpay.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.oneclickpay.R
import com.example.oneclickpay.dashboard.DashBoardScreen
import com.example.oneclickpay.ui.theme.OneClickPayTheme
import org.koin.compose.viewmodel.koinViewModel

class DashBoardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneClickPayTheme {
                val borderColor = colorResource(R.color.gary_white)
                HomeActivity(borderColor)
            }
        }
    }
}

@Composable
fun HomeActivity(
    borderColor: Color, viewModel: UserInfoViewModel = koinViewModel()
) {

    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()

    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = { HomeTopBar(borderColor = borderColor, uiState = uiState) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    uiState: UserInfoStates,
    borderColor: Color
) {
    val userName = when (uiState) {
        is UserInfoStates.Success -> uiState.userInfoModel.name
        else -> ""
    }

    val imageUrl = (uiState as? UserInfoStates.Success)?.userInfoModel?.avatar

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.app_bar_color),
        ),
        modifier = Modifier.drawBehind {
            drawLine(
                color = borderColor,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 2.dp.toPx()
            )
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = imageUrl ?: R.drawable.trans_ic,
                    placeholder = painterResource(R.drawable.trans_ic),
                    error = painterResource(R.drawable.trans_ic),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f))
                )

                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = userName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    )
}