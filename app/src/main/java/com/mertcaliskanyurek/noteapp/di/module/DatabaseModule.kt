package com.mertcaliskanyurek.noteapp.di.module

import android.content.Context
import androidx.room.Room
import com.mertcaliskanyurek.noteapp.data.AppDatabase
import com.mertcaliskanyurek.noteapp.data.dao.NoteRoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "note_app.db").build()

    @Provides
    @Singleton
    fun noteRoomDao(db: AppDatabase): NoteRoomDao = db.noteDao()
}