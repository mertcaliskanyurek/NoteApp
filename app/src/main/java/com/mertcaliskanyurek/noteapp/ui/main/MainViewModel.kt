package com.mertcaliskanyurek.noteapp.ui.main

import androidx.lifecycle.*

import com.mertcaliskanyurek.noteapp.domain.NoteEntity
import com.mertcaliskanyurek.noteapp.data.source.SourceResult
import com.mertcaliskanyurek.noteapp.domain.note.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addNote: AddNote,
    private val deleteNote: DeleteNote,
    private val undoDelete: UndoDeleteNote,
    private val getAllNotes: GetAllNotes,
    private val updateNote: UpdateNote
) : ViewModel() {

    fun getNotes() = liveData<SourceResult<List<NoteEntity>>>(Dispatchers.IO) {
        emit(getAllNotes.invoke())
    }

    fun addNew(noteEntity: NoteEntity) = liveData<SourceResult<NoteEntity>>(Dispatchers.IO) {
        emit(addNote.invoke(noteEntity))
    }

    fun update(noteEntity: NoteEntity) = liveData<SourceResult<NoteEntity>>(Dispatchers.IO) {
        emit(updateNote.invoke(noteEntity))
    }

    fun delete(noteEntity: NoteEntity) = liveData<SourceResult<NoteEntity>>(Dispatchers.IO) {
        emit(deleteNote.invoke(noteEntity))
    }

    fun undoDeleteNote(noteEntity: NoteEntity) = liveData<SourceResult<NoteEntity>>(Dispatchers.IO) {
        emit(undoDelete.invoke(noteEntity))
    }
}