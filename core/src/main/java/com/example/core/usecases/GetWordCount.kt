package com.example.core.usecases

import com.example.core.data.Note
import com.example.core.repository.NoteRepository

class GetWordCount() {

    operator fun invoke(note: Note) = getCount(note.title) + getCount(note.content)

    private fun getCount(str: String) =
        str.split(" ", "/n").filter { it.contains(Regex(".*[a-zA-Z].*")) }.count()
}