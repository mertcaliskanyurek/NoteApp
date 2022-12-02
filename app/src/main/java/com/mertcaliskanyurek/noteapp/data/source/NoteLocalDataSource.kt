package com.mertcaliskanyurek.noteapp.data.source

import com.mertcaliskanyurek.noteapp.data.dao.NoteRoomDao
import com.mertcaliskanyurek.noteapp.domain.NoteEntity

class NoteLocalDataSource (private val noteDao: NoteRoomDao):
    BaseLocalDataSource<NoteEntity>(noteDao)  {

    fun get(id: Long): SourceResult<NoteEntity> = try {
        val res = noteDao.get(id)
        res?.let {
            SourceResult.Success(res)
        }?: run {
            SourceResult.Failure(Exception("Entity could not find"))
        }
    }catch (e: Exception) {
        SourceResult.Failure(e)
    }

    fun putInTrash(note: NoteEntity): SourceResult<NoteEntity> = try {
        noteDao.putTrash(note.id)
        SourceResult.Success(note)
    }catch (e: Exception) {
        SourceResult.Failure(e)
    }

    fun getAll(): SourceResult<List<NoteEntity>> = try {
        val res = noteDao.get()
        SourceResult.Success(res)
    }catch (e: Exception) {
        SourceResult.Failure(e)
    }
    fun undoTrash(note: NoteEntity): SourceResult<NoteEntity> = try {
        noteDao.undoTrash(note.id)
        SourceResult.Success(note)
    }catch (e: Exception) {
        SourceResult.Failure(e)
    }
}