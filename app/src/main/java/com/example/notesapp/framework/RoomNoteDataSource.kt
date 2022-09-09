package com.example.notesapp.framework

import android.content.Context
import com.example.core.data.Note
import com.example.core.repository.NoteDataSource
import com.example.notesapp.framework.db.DatabaseService
import com.example.notesapp.framework.db.NoteEntity

class RoomNoteDataSource(context: Context) : NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).noteDao()
    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))
    override suspend fun updateNote(note: Note) = noteDao.updateUsers(NoteEntity.fromNote(note))
    override suspend fun getId(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAllList() = noteDao.getNoteList().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))

}