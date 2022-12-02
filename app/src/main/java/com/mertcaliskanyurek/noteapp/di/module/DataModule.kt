package com.mertcaliskanyurek.noteapp.di.module

import com.mertcaliskanyurek.noteapp.data.repository.NoteRepository
import com.mertcaliskanyurek.noteapp.data.dao.NoteRoomDao
import com.mertcaliskanyurek.noteapp.data.source.NoteLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun noteDataSource(noteRoomDao: NoteRoomDao): NoteLocalDataSource = NoteLocalDataSource(noteRoomDao)

    @Provides
    @Singleton
    fun noteRepository(noteDataSource: NoteLocalDataSource): NoteRepository = NoteRepository(noteDataSource)
}