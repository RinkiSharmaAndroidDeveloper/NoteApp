package com.example.notesapp.framework.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface NoteDao {
    @Insert(onConflict = REPLACE)
    suspend fun addNoteEntity(noteEntity: NoteEntity)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteEntity(id: Long): NoteEntity?

    @Update
    fun updateUsers(note: NoteEntity)

    @Query("SELECT * FROM note")
    suspend fun getNoteList(): List<NoteEntity>

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)
}