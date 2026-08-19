package com.example.oneclickpay.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.oneclickpay.R
import com.example.oneclickpay.card.addnewcard.CardsScreen
import com.example.oneclickpay.card.allcard.MyCardsScreen
import com.example.oneclickpay.dashboard.DashBoardScreen
import com.example.oneclickpay.sendmoney.SendMoneyScreen
import com.example.oneclickpay.transactions.TransactionsScreen
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
    borderColor: Color,
    viewModel: UserInfoViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()

    var isExpanded by remember { mutableStateOf(false) }
    val showBottomBar = currentRoute != ScreenRoute.Transfer.route

    BackHandler(enabled = isExpanded || currentRoute != ScreenRoute.Dashboard.route) {
        if (isExpanded) {
            isExpanded = false
        } else {
            navController.popBackStack()
        }
    }

    Scaffold(
        containerColor = colorResource(R.color.background),
        topBar = {
            when {
                currentRoute == ScreenRoute.Transfer.route -> {
                    AppBaseTopBar(
                        title = { TopBarTitle("Send Money") },
                        borderColor = borderColor,
                        navigationIcon = { BackButton { navController.popBackStack() } }
                    )
                }

                currentRoute == ScreenRoute.MyCard.route -> {
                    AppBaseTopBar(
                        title = { TopBarTitle("NexusPay") },
                        borderColor = borderColor,
                        isCenterAligned = true,
                        navigationIcon = { BackButton { navController.popBackStack() } }
                    )
                }

                currentRoute == ScreenRoute.Dashboard.route && isExpanded -> {
                    AppBaseTopBar(
                        title = { TopBarTitle("All Transactions", fontSize = 20.sp) },
                        borderColor = borderColor,
                        navigationIcon = { BackButton { isExpanded = false } }
                    )
                }

                else -> {
                    HomeTopBar(uiState = uiState, borderColor = borderColor)
                }
            }
        },
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigationBar(
                    currentRoute = currentRoute,
                    borderColor = borderColor,
                    onNavigate = { route ->
                        isExpanded = false
                        navController.navigateToTab(route)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.Dashboard.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(ScreenRoute.Dashboard.route) {
                DashBoardScreen(
                    isExpanded = isExpanded,
                    onExpandedChange = { isExpanded = it },
                    onClick = { navController.navigate(ScreenRoute.Transfer.route) }
                )
            }
            composable(ScreenRoute.MyCard.route) {
                MyCardsScreen(
                    onAddNewCardClick = { navController.navigate(ScreenRoute.Cards.route) }
                )
            }
            composable(ScreenRoute.Transactions.route) { TransactionsScreen() }
            composable(ScreenRoute.Transfer.route) {
                SendMoneyScreen(
                    onConfirmTransfer = { _, _, _ ->
                        navController.popBackStack()
                    }
                )
            }
            composable(ScreenRoute.Cards.route) { CardsScreen() }
        }
    }
}

@Composable
fun TopBarTitle(text: String, fontSize: androidx.compose.ui.unit.TextUnit = 18.sp) {
    Text(
        text = text,
        color = Color.White,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun AppBottomNavigationBar(
    currentRoute: String?,
    borderColor: Color,
    onNavigate: (String) -> Unit
) {
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

        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(item.iconRes),
                        contentDescription = stringResource(item.labelRes)
                    )
                },
                label = { Text(text = stringResource(item.labelRes)) },
                colors = navColors
            )
        }
    }
}

@Composable
fun HomeTopBar(
    uiState: UserInfoStates,
    borderColor: Color
) {
    val userName = (uiState as? UserInfoStates.Success)?.userInfoModel?.name.orEmpty()
    val imageUrl = (uiState as? UserInfoStates.Success)?.userInfoModel?.avatar

    AppBaseTopBar(
        borderColor = borderColor,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                Text(
                    text = userName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp)
                )
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

private fun NavController.navigateToTab(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

data class BottomNavItem(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int
)

val bottomNavItems = listOf(
    BottomNavItem(ScreenRoute.Dashboard.route, R.drawable.home_ic, R.string.home),
    BottomNavItem(ScreenRoute.MyCard.route, R.drawable.card_ic, R.string.cards),
    BottomNavItem(ScreenRoute.Transactions.route, R.drawable.transaction_ic, R.string.transactions)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBaseTopBar(
    title: @Composable () -> Unit,
    borderColor: Color,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null,
    isCenterAligned: Boolean = false
) {
    val containerColor = colorResource(R.color.app_bar_color)
    val modifier = Modifier.drawBehind {
        drawLine(
            color = borderColor,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 2.dp.toPx()
        )
    }

    if (isCenterAligned) {
        CenterAlignedTopAppBar(
            title = title,
            modifier = modifier,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = containerColor),
            navigationIcon = { navigationIcon?.invoke() },
            actions = { actions?.invoke() }
        )
    } else {
        TopAppBar(
            title = title,
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(containerColor = containerColor),
            navigationIcon = { navigationIcon?.invoke() },
            actions = { actions?.invoke() }
        )
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}