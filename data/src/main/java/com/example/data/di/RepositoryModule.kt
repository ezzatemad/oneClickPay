package com.example.data.di


import com.example.data.repoimpl.RecentTransactionsRepoImpl
import com.example.data.repoimpl.UserInfoRepoImpl
import com.example.domain.recenttranscations.repo.RecentTransactionRepo
import com.example.domain.recenttranscations.repo.UserInfoRepo
import org.koin.dsl.module


val repositoryModule = module {

    single<RecentTransactionRepo> {
        RecentTransactionsRepoImpl(get())
    }

    single<UserInfoRepo> {
        UserInfoRepoImpl(get())
    }

}

