package com.example.data.di


import com.example.data.repoimpl.RecentTransactionsRepoImpl
import com.example.data.repoimpl.UserInfoRepoImpl
import com.example.domain.recenttranscations.repo.recenttransactionrepo.RecentTransactionRepo
import com.example.domain.recenttranscations.repo.userinfo.UserInfoRepo
import org.koin.dsl.module


val repositoryModule = module {

    single<RecentTransactionRepo> {
        RecentTransactionsRepoImpl(get())
    }

    single<UserInfoRepo> {
        UserInfoRepoImpl(get())
    }

}

