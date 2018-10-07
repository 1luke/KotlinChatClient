package com.example.luke.kotlinchatclient

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: Message)

    @Query("SELECT * from messages ORDER BY timestamp ASC")
    fun messages(): LiveData<List<Message>>

    @Query("DELETE FROM messages")
    fun deleteAll()

//    // TODO: Test if this works!
//    @Query("SELECT MIN(senderId) AS senderId, id, timestamp, type, content, sender\n" +
//            "FROM messages ORDER BY timestamp ASC"
//    )
//    fun senders(): List<Message>
}