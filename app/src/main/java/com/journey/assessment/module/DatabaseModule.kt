package com.journey.assessment.module

import android.content.Context
import com.journey.assessment.dao.CommentDao
import com.journey.assessment.database.JourneyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Ricky Chen
 * Use this database module to adapter db
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideJourneyDatabase(@ApplicationContext context: Context): JourneyDatabase {
        return JourneyDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCommentDao(journeyDatabase: JourneyDatabase): CommentDao {
        return journeyDatabase.commentDao()
    }
}