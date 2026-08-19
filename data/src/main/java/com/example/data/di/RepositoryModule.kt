package com.example.data.di


import com.example.data.repoimpl.mycard.AddCardRepoImpl
import com.example.data.repoimpl.AllUsersRepoImpl
import com.example.data.repoimpl.RecentTransactionsRepoImpl
import com.example.data.repoimpl.SendMoneyRepoImpl
import com.example.data.repoimpl.UserInfoRepoImpl
import com.example.data.repoimpl.mycard.MyCardsRepoImpl
import com.example.domain.recenttranscations.repo.mycards.AddCardRepo
import com.example.domain.recenttranscations.repo.AllUsersRepo
import com.example.domain.recenttranscations.repo.RecentTransactionRepo
import com.example.domain.recenttranscations.repo.SendMoneyRepo
import com.example.domain.recenttranscations.repo.UserInfoRepo
import com.example.domain.recenttranscations.repo.mycards.MyCardsRepo
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
        AllUsersRepoImpl(get(), get())
    }
    single<AddCardRepo> {
        AddCardRepoImpl(get())
    }
    single<MyCardsRepo> {
        MyCardsRepoImpl(get())
    }
}

