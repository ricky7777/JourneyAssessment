package com.journey.assessment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.journey.assessment.dao.CommentDao
import com.journey.assessment.model.CommentEntity

/**
 * @author Ricky Chen
 * local storage database
 */
@Database(entities = [CommentEntity::class], version = 1)
abstract class JourneyDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao

    companion object {
        private const val DB_NAME = "journey_database"

        @Volatile
        private var INSTANCE: JourneyDatabase? = null

        fun getDatabase(context: Context): JourneyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JourneyDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}