package com.example.data.di


import com.example.data.repoimpl.AllUsersRepoImpl
import com.example.data.repoimpl.RecentTransactionsRepoImpl
import com.example.data.repoimpl.SendMoneyRepoImpl
import com.example.data.repoimpl.UserInfoRepoImpl
import com.example.domain.recenttranscations.repo.AllUsersRepo
import com.example.domain.recenttranscations.repo.RecentTransactionRepo
import com.example.domain.recenttranscations.repo.SendMoneyRepo
import com.example.domain.recenttranscations.repo.UserInfoRepo
import org.koin.dsl.module


val repositoryModule = module {

    single<RecentTransactionRepo> {
        RecentTransactionsRepoImpl(get(), get(), get())
    }

    single<UserInfoRepo> {
        UserInfoRepoImpl(get(), get(), get())
    }
    single<SendMoneyRepo> {
        SendMoneyRepoImpl(get(), get(), get())
    }
    single<AllUsersRepo> {
        AllUsersRepoImpl(get(),get())
    }
}

