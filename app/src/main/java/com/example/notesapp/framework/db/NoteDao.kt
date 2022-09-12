package com.example.notesapp.framework.db

import androidx.room.*


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteEntity(noteEntity: NoteEntity)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteEntity(id: Long): NoteEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsers(note: NoteEntity)

    @Query("SELECT * FROM note")
    suspend fun getNoteList(): List<NoteEntity>

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)
}