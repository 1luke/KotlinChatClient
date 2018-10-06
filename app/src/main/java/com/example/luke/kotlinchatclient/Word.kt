package com.example.luke.kotlinchatclient

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "word")
data class Word(@PrimaryKey val word: String)