package com.mertcaliskanyurek.noteapp.data.repository

import com.mertcaliskanyurek.noteapp.data.source.NoteLocalDataSource
import com.mertcaliskanyurek.noteapp.domain.NoteEntity
import com.mertcaliskanyurek.noteapp.data.source.SourceResult

class NoteRepository(private val noteLocalDataSource: NoteLocalDataSource):
    BaseRepository<NoteEntity>(noteLocalDataSource)
{
        fun getAll(): SourceResult<List<NoteEntity>> = noteLocalDataSource.getAll()
        fun get(id: Long): SourceResult<NoteEntity> = noteLocalDataSource.get(id)
        fun putInTrash(note: NoteEntity): SourceResult<NoteEntity> = noteLocalDataSource.putInTrash(note)
        fun undoTrash(note: NoteEntity): SourceResult<NoteEntity> = noteLocalDataSource.undoTrash(note)
}