package com.mertcaliskanyurek.noteapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var title: String,
    var text: String,
    var imageUrl: String,
    val createDate: Date,
    var lastEditDate: Date,
    var inTrash: Boolean = false
)