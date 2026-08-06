package com.example.oneclickpay


import android.app.Application
import com.example.data.di.databaseModule
import com.example.data.di.networkModule
import com.example.data.di.repositoryModule
import com.example.oneclickpay.di.useCaseModule
import com.example.oneclickpay.di.viewModalModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@MyApp)
            modules(
                repositoryModule,
                networkModule,
                useCaseModule,
                viewModalModule,
                databaseModule
            )
        }
    }
}