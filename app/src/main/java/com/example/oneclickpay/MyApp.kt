package com.example.oneclickpay

import android.app.Application
import androidx.work.Configuration
import com.example.data.di.databaseModule
import com.example.data.di.networkModule
import com.example.data.di.repositoryModule
import com.example.oneclickpay.di.useCaseModule
import com.example.oneclickpay.di.viewModalModule
import com.example.oneclickpay.di.workerModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class MyApp : Application(), Configuration.Provider, KoinComponent {

    private val koinWorkerFactory: KoinWorkerFactory by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            workManagerFactory()
            modules(
                repositoryModule,
                networkModule,
                useCaseModule,
                viewModalModule,
                databaseModule,
                workerModule
            )
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(koinWorkerFactory)
            .build()
}