package com.mertcaliskanyurek.noteapp.domain.note

import com.mertcaliskanyurek.noteapp.data.repository.NoteRepository
import javax.inject.Inject

class GetAllNotes @Inject constructor(private val noteRepository: NoteRepository)
{
    operator fun invoke() = noteRepository.getAll()
}