package com.annada.android.sample.chucknorris.base

import androidx.lifecycle.ViewModel
import com.annada.android.sample.chucknorris.di.component.DaggerViewModelInjector
import com.annada.android.sample.chucknorris.di.component.ViewModelInjector
import com.annada.android.sample.chucknorris.di.module.NetworkModule
import com.annada.android.sample.chucknorris.ui.main.QuoteListViewModel
import com.annada.android.sample.chucknorris.ui.main.JokeViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is QuoteListViewModel -> injector.inject(this)
            is JokeViewModel -> injector.inject(this)
        }
    }
}