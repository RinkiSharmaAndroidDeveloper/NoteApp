package com.example.core.repository

import com.example.core.data.Note

class NoteRepository(private val noteDataSource: NoteDataSource) {

    suspend fun getAllElements() = noteDataSource.getAllList()
    suspend fun getID(id: Long) = noteDataSource.getId(id)
    suspend fun addNotes(note: Note) = noteDataSource.add(note)
    suspend fun removeNote(note: Note) = noteDataSource.remove(note)
    suspend fun updateNote(note: Note) = noteDataSource.updateNote(note)

}