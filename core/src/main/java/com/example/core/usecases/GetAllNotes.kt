package com.example.core.usecases

import com.example.core.repository.NoteRepository

class GetAllNotes(private val noteRepository: NoteRepository) {

    suspend operator fun invoke() = noteRepository.getAllElements()

}