package com.example.oneclickpay.di

import com.example.domain.recenttranscations.usecase.RecentTransactionUseCase
import com.example.domain.recenttranscations.usecase.UserInfoUseCase
import org.koin.dsl.module



val useCaseModule = module {

    factory {
        RecentTransactionUseCase(get())
    }

    factory {
        UserInfoUseCase(get())
    }
}