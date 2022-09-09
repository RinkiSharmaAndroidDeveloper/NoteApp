package com.example.core.usecases

import com.example.core.data.Note
import com.example.core.repository.NoteRepository

class UpdateNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) = noteRepository.updateNote(note)
}
