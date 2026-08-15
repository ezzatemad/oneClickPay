package com.example.oneclickpay.di

import com.example.data.worker.SendMoneyWorker
import com.example.data.worker.TransactionSyncWorker
import com.example.data.worker.UserInfoSyncWorker
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    worker { SendMoneyWorker(get(), get(), get(), get()) }
    worker { TransactionSyncWorker(get(), get(), get(), get()) }
    worker { UserInfoSyncWorker(get(), get(), get(), get()) }
}