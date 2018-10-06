package com.example.luke.kotlinchatclient

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Word::class], version = 1)
public abstract class WordDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    /// Singleton
    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun database(context: Context): WordDatabase {
            return  INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word_database").build()
                db
            }
        }
    }
}