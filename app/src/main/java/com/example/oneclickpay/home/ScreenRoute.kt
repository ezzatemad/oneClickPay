package com.example.oneclickpay.home

sealed class ScreenRoute(val route: String) {
    object Dashboard : ScreenRoute("dashboard")
    object Cards : ScreenRoute("cards")
    object Transactions : ScreenRoute("transactions")
    object Transfer : ScreenRoute("transfer")
    object MyCard : ScreenRoute("myCards")
}