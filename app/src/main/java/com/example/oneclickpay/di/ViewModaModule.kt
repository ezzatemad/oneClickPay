package com.example.oneclickpay.di

import com.example.oneclickpay.dashboard.DashBoardScreenViewModel
import com.example.oneclickpay.home.UserInfoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModalModule = module {

    viewModel {
        DashBoardScreenViewModel(get())
    }

    viewModel {
        UserInfoViewModel(get())
    }
}