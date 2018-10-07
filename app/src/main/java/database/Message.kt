package com.example.luke.kotlinchatclient

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "message")
data class Message(@PrimaryKey val word: String)