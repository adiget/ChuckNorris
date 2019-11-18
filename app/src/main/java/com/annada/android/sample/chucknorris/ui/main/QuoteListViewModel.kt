package com.annada.android.sample.chucknorris.ui.main

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.annada.android.sample.chucknorris.R
import com.annada.android.sample.chucknorris.api.ApiService
import com.annada.android.sample.chucknorris.base.BaseViewModel
import com.annada.android.sample.chucknorris.model.daos.JokeDao
import com.annada.android.sample.chucknorris.model.entities.Joke
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject

class QuoteListViewModel(private val jokeDao: JokeDao) : BaseViewModel() {
    @Inject
    lateinit var api: ApiService
    val quoteListAdapter: QuoteListAdapter = QuoteListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadData() }

    private val viewModelJob = Job()
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        onRetrieveQuoteListFinish()
        when (error) {
            is Exception -> onRetrieveQuoteListError()
        }
    }

    var isRefreshing = ObservableField(false)

    private var totalJokes: Int? = 0
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + errorHandler)
    private val bgDispatcher = Dispatchers.IO

    init {
        GlobalScope.launch(bgDispatcher) {
            totalJokes = getTotalNumberOfJokes()

            Log.d(TAG, "Total Number of Jokes:- " + totalJokes)
        }

        loadData()
    }

    private fun loadData() {


        uiScope.launch {
            onRetrieveQuoteListStart()

            var quoteList = getDbJokes()

            if (quoteList?.isNotEmpty() == false) {

                quoteList = api.getRandomJokes((RAMDOM_MIN..RANDOM_MAX).random()).value

                quoteList?.let { insertAll(it) }
            }

            quoteList?.let { onRetrieveQuoteListSuccess(it) }

            onRetrieveQuoteListFinish()
        }
    }

    fun loadMore() {

        uiScope.launch {

            if (isRefreshing.get() == true) {
                return@launch
            }

            if (getItemCount() == totalJokes) {
                onRetrieveJokesNoMoreError()
                return@launch
            }

            isRefreshing.set(true)


            onRetrieveQuoteListStart()

            var quoteList = api.getRandomJokes((RAMDOM_MIN..RANDOM_MAX).random()).value

            quoteList?.let { insertAll(it) }

            quoteList = getDbJokes()

            quoteList?.let { onRetrieveQuoteListSuccess(it) }

            onRetrieveQuoteListFinish()

            isRefreshing.set(false)
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    private suspend fun onRetrieveQuoteListStart() {
        withContext(Main) {
            loadingVisibility.value = View.VISIBLE
            errorMessage.value = null
        }
    }

    private fun onRetrieveQuoteListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveQuoteListSuccess(quoteList: List<Joke>) {
        quoteListAdapter.updateQuoteList(quoteList)
    }

    fun getItemCount(): Int {
        return quoteListAdapter.itemCount
    }

    private fun onRetrieveQuoteListError() {
        errorMessage.value = R.string.quote_error
    }

    private fun onRetrieveJokesNoMoreError() {
        errorMessage.value = R.string.no_more_error
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

    private suspend fun insert(joke: Joke) {
        return withContext(bgDispatcher) {
            jokeDao.insert(joke)
        }
    }

    private suspend fun deleteAll() {
        return withContext(bgDispatcher) {
            jokeDao.deleteAll()
        }
    }

    private suspend fun getTotalNumberOfJokes(): Int? {
        val apiResponse = api.getJokesCount()

        return apiResponse.value
    }

    fun filterNoExplicitCategory() {


        uiScope.launch {

            onRetrieveQuoteListStart()

            val noExplicitJoke = api.getRandomJokesExcludeCategory().value

            noExplicitJoke?.let {
                deleteAll()
                insert(it)

                val quoteList = getDbJokes()

                quoteList?.let { onRetrieveQuoteListSuccess(it) }
            }

            onRetrieveQuoteListFinish()

        }

    }

    companion object {
        private val TAG = QuoteListViewModel::class.java.simpleName
        val RAMDOM_MIN = 8
        val RANDOM_MAX = 21
    }
}