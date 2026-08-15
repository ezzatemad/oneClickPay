package com.example.data.di

import androidx.room.Room
import com.example.data.db.AppDatabase
import com.example.data.worker.SendMoneyWorker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "transactions_db"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<AppDatabase>().transactionDao() }
    single { get<AppDatabase>().userInfoDao() }
//    single { get<AppDatabase>().sendTransactionDao() }
    single { get<AppDatabase>().allUserDao() }
}
