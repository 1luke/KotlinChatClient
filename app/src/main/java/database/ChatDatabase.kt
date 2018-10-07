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

@Database(entities = [Message::class], version = 1)
public abstract class ChatDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao

    /// Singleton
    companion object {
        @Volatile
        private var INSTANCE: ChatDatabase? = null

        // Note: onOpen() launches a coroutine on the IO Dispatcher.
        // To launch a coroutine we need a CoroutineScope.
        // Require a coroutine scope as parameter
        fun database(context: Context, scope: CoroutineScope): ChatDatabase {
            return  INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                        context.applicationContext,
                        ChatDatabase::class.java,
                        "chat_database")
                        .addCallback(ChatDatabaseCallback(scope))
                        .build()
                db
            }
        }
    }

    private class ChatDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populate(database.messageDao())
                }
            }
        }

        private fun populate(messageDao: MessageDao) {
            messageDao.deleteAll()
            for(i in 1..10){
                messageDao.insert(mockMessage("$i"))
            }
        }
    }
}