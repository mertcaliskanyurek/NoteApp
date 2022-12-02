package com.mertcaliskanyurek.noteapp.domain.note

import com.mertcaliskanyurek.noteapp.data.repository.NoteRepository
import com.mertcaliskanyurek.noteapp.domain.NoteEntity
import javax.inject.Inject

class UndoDeleteNote  @Inject constructor(private val noteRepository: NoteRepository) {
    operator fun invoke(noteEntity: NoteEntity) = noteRepository.undoTrash(noteEntity)
}