package com.example.notesapp.framework

import com.example.core.usecases.*

data class UseCase(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNotes: GetNotes,
    val removeNote: RemoveNote,
    val updateNote: UpdateNote,
    val wordCount: GetWordCount
)
