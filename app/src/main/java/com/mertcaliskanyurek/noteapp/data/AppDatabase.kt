package com.mertcaliskanyurek.noteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mertcaliskanyurek.noteapp.data.dao.NoteRoomDao
import com.mertcaliskanyurek.noteapp.domain.NoteEntity


@Database(
    entities = [
        NoteEntity::class
    ], version = 1, exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteRoomDao
}