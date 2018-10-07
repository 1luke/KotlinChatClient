package com.example.luke.kotlinchatclient

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

@Database(entities = [Word::class], version = 1)
public abstract class WordDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    /// Singleton
    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        // Note: onOpen() launches a coroutine on the IO Dispatcher.
        // To launch a coroutine we need a CoroutineScope.
        // Require a coroutine scope as parameter
        fun database(context: Context, scope: CoroutineScope): WordDatabase {
            return  INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word_database")
                        .addCallback(WordDatabaseCallback(scope))
                        .build()
                db
            }
        }
    }

    private class WordDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populate(database.wordDao())
                }
            }
        }

        private fun populate(wordDao: WordDao) {
            wordDao.deleteAll()

            for(i in 1..10){
                wordDao.insert(Word("Word: $i"))
            }
        }
    }
}