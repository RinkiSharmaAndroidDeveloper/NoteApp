package com.example.notesapp.presentation

import com.example.core.data.Note

interface ListAction {
    fun click(note: Note)
}