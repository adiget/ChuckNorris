package com.annada.android.sample.chucknorris.di.component

import com.annada.android.sample.chucknorris.di.module.NetworkModule
import com.annada.android.sample.chucknorris.ui.main.QuoteListViewModel
import com.annada.android.sample.chucknorris.ui.main.JokeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(quoteListViewModel: QuoteListViewModel)

    fun inject(jokeViewModel: JokeViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}