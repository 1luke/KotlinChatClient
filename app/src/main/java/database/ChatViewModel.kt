package com.example.luke.kotlinchatclient

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class ChatViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ChatRepository

    val messages: LiveData<List<Message>>

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val db = ChatDatabase.database(application, scope)
        repository = ChatRepository(db.messageDao())
        messages = repository.messages
    }

    // implementation of insert() is completely hidden from the UI. We want the insert()
    // method to be called away from the main thread, so we're launching a new coroutine,
    // based on the coroutine scope defined previously. Because we're doing a database
    // operation, we're using the IO Dispatcher.
    fun insert(message: Message) = scope.launch(Dispatchers.IO) {
        repository.insert(message)
    }

    // deinit
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}