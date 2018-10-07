package com.example.luke.kotlinchatclient

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class ChatRepository(private val messageDao: MessageDao) {

    val messages: LiveData<List<Message>> = messageDao.messages()

    @WorkerThread
    suspend fun insert(message: Message) {
        messageDao.insert(message)
    }
}