package com.example.core.repository

import com.example.core.data.Note

interface NoteDataSource {
    suspend fun add(note: Note)
    suspend fun getId(id: Long): Note?
    suspend fun getAllList(): List<Note>
    suspend fun remove(note: Note)
    suspend fun updateNote(note: Note)
}