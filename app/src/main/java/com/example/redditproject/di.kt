package com.example.redditproject

import android.app.Application
import com.example.data.repository.MainRepository
import com.example.data.source.RemoteDataSource
import com.example.redditproject.data.server.RedditDataSource
import com.example.redditproject.data.server.RedditNetwork
import com.example.redditproject.ui.MainActivity
import com.example.redditproject.ui.MainViewModel
import com.example.usecases.GetFeedUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

val appModule = module {
    factory<RemoteDataSource> { RedditDataSource(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single { RedditNetwork() }
}

val dataModule = module {
    factory { MainRepository(get()) }
}

val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
        scoped { GetFeedUseCase(get()) }
    }
}