package com.annada.android.sample.chucknorris.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.annada.android.sample.chucknorris.R
import com.annada.android.sample.chucknorris.api.ApiService
import com.annada.android.sample.chucknorris.base.BaseViewModel
import com.annada.android.sample.chucknorris.model.daos.JokeDao
import com.annada.android.sample.chucknorris.model.entities.Joke
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import javax.inject.Inject

class QuoteListViewModel(private val jokeDao: JokeDao) : BaseViewModel() {
    @Inject
    lateinit var api: ApiService
    val quoteListAdapter: QuoteListAdapter = QuoteListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadData() }

    private lateinit var subscription: Disposable

    private val viewModelJob = Job()
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        when (error) {
            is Exception -> onRetrieveQuoteListError()
        }
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + errorHandler)
    private val bgDispatcher = Dispatchers.IO

    init {
        loadData()
    }

    private fun loadData() {
        onRetrieveQuoteListStart()

        uiScope.launch {
            var quoteList = getDbJokes()

            if (quoteList?.isNotEmpty() == false) {

                quoteList = api.getRandomJokes((RAMDOM_MIN..RANDOM_MAX).random()).value

                quoteList?.let { insertAll(it) }
            }

            quoteList?.let { onRetrieveQuoteListSuccess(it) }

            onRetrieveQuoteListFinish()
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()

        viewModelJob.cancel()
    }

    private fun onRetrieveQuoteListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveQuoteListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveQuoteListSuccess(quoteList: List<Joke>) {
        quoteListAdapter.updateQuoteList(quoteList)
    }

    private fun onRetrieveQuoteListError() {
        errorMessage.value = R.string.quote_error
    }

    private suspend fun getDbJokes(): List<Joke>? {
        return withContext(bgDispatcher) {
            jokeDao.all
        }
    }

    private suspend fun insertAll(jokes: List<Joke>) {
        return withContext(bgDispatcher) {
            jokeDao.insertAll(*jokes.toTypedArray())
        }
    }

    companion object{
        val RAMDOM_MIN = 8
        val RANDOM_MAX = 21
    }
}