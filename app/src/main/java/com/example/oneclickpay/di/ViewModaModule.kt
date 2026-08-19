package com.example.oneclickpay.di

import com.example.oneclickpay.card.addnewcard.CardViewModel
import com.example.oneclickpay.card.allcard.MyCardsViewModel
import com.example.oneclickpay.dashboard.DashBoardScreenViewModel
import com.example.oneclickpay.home.UserInfoViewModel
import com.example.oneclickpay.sendmoney.SendMoneyViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModalModule = module {

    viewModel {
        DashBoardScreenViewModel(get())
    }

    viewModel {
        UserInfoViewModel(get())
    }

    viewModel {
        SendMoneyViewModel(get(),get())
    }

    viewModel {
        CardViewModel(get())
    }
    viewModel {
        MyCardsViewModel(get())
    }
}