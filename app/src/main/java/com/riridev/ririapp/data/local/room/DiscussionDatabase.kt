package com.riridev.ririapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.riridev.ririapp.data.local.room.dao.DiscussionDao
import com.riridev.ririapp.data.local.room.dao.RemoteKeysDao
import com.riridev.ririapp.data.local.room.entity.DiscussionEntity
import com.riridev.ririapp.data.local.room.entity.RemoteKeys

@Database(
    entities = [DiscussionEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class DiscussionDatabase: RoomDatabase() {
    abstract fun discussionDao(): DiscussionDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    companion object {
        @Volatile
        private var INSTANCE: DiscussionDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): DiscussionDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DiscussionDatabase::class.java, "discussion_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}