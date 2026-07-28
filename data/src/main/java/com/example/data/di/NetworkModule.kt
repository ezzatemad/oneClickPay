package com.example.data.di
import com.example.composereview.mainascreen.Constants
import com.example.data.apiservices.RecentTransactionApi
import com.example.data.apiservices.UserInfoApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    single {
        HttpClient(OkHttp) {

            install(ContentNegotiation) {
                json(json)
            }

            defaultRequest {
                url(Constants.BASE_URL)
            }
        }
    }
    single {
        RecentTransactionApi(get())
    }

    single {
        UserInfoApi(get())
    }
}

