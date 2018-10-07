package com.example.luke.kotlinchatclient

import android.arch.persistence.room.*
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "messages")
data class Message(@PrimaryKey
                   val id: String,
                   val timestamp: Long,
                   val type: String,
                   val content: String,
                   val senderId: String,
                   val sender: String
)

fun mockMessage(id: String): Message {
    return Message(id, Date().time, "message", "Mock message", "sender1", "Sender Name")
}