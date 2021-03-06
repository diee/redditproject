package com.example.redditproject

import android.app.Application
import com.example.data.repository.MainRepository
import com.example.data.source.LocalDataSource
import com.example.data.source.RemoteDataSource
import com.example.redditproject.data.database.RedditProjectDataBase
import com.example.redditproject.data.database.RoomDataSource
import com.example.redditproject.data.server.RedditDataSource
import com.example.redditproject.data.server.RedditNetwork
import com.example.redditproject.ui.detail.DetailFragment
import com.example.redditproject.ui.detail.DetailViewModel
import com.example.redditproject.ui.main.MainActivity
import com.example.redditproject.ui.main.MainViewModel
import com.example.usecases.*
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
    single { RedditProjectDataBase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { RedditDataSource(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single { RedditNetwork() }
}

val dataModule = module {
    factory { MainRepository(get(), get()) }
}

val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(), get(), get(), get()) }
        scoped { GetFeedUseCase(get()) }
        scoped { DismissFeedDataUseCase(get()) }
        scoped { DismissAllFeedUseCase(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (feedId: String) -> DetailViewModel(feedId, get(), get(), get()) }
        scoped { GetFeedDataByIdUseCase(get()) }
        scoped { ReadFeedDataUseCase(get()) }
    }
}