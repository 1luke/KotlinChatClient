package com.example.luke.kotlinchatclient

import android.arch.persistence.room.*

@Entity(tableName = "messages")
data class Message(@PrimaryKey
                   val id: String,
                   val timestamp: Long,
                   val type: String,
                   val content: String,
                   val senderId: String,
                   val sender: String
)

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: Message)

    @Query("SELECT * from messages ORDER BY timestamp ASC")
    fun messages(): List<Message>

    @Query("SELECT * FROM messages")
    fun deleteAll()
}