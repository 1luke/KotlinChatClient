package com.example.luke.kotlinchatclient

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class WordViewModel(application: Application): AndroidViewModel(application) {

    private val repository: WordRepository
    private val allWords: LiveData<List<Word>>

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        repository = WordRepository(WordDatabase.database(application, scope).wordDao())
        allWords = repository.allWords
    }

    // implementation of insert() is completely hidden from the UI. We want the insert()
    // method to be called away from the main thread, so we're launching a new coroutine,
    // based on the coroutine scope defined previously. Because we're doing a database
    // operation, we're using the IO Dispatcher.
    fun insert(word: Word) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    // deinit
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}