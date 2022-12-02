package com.mertcaliskanyurek.noteapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.mertcaliskanyurek.noteapp.domain.NoteEntity

@Dao
interface NoteRoomDao : BaseRoomDao<NoteEntity> {

    @Query("SELECT * FROM notes WHERE inTrash == 0")
    fun get() : List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id AND inTrash == 0")
    fun get(id: Long) :NoteEntity?

    @Query("UPDATE notes SET inTrash = 1 WHERE id = :id")
    fun putTrash(id: Long)

    @Query("UPDATE notes SET inTrash = 0 WHERE id = :id")
    fun undoTrash(id: Long)
}