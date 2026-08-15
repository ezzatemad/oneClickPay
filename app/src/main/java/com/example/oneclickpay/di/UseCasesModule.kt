package com.example.oneclickpay.di

import com.example.data.worker.SendMoneyWorker
import com.example.data.worker.TransactionSyncWorker
import com.example.data.worker.UserInfoSyncWorker
import com.example.domain.recenttranscations.usecase.AllUserUseCase
import com.example.domain.recenttranscations.usecase.RecentTransactionUseCase
import com.example.domain.recenttranscations.usecase.SendMoneyUseCase
import com.example.domain.recenttranscations.usecase.UserInfoUseCase
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module


val useCaseModule = module {

    factory {
        RecentTransactionUseCase(get())
    }

    factory {
        UserInfoUseCase(get())
    }

    factory {
        SendMoneyUseCase(get())
    }
    factory {
        AllUserUseCase(get())
    }
}

