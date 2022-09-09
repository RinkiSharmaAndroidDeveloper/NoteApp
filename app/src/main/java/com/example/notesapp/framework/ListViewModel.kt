package com.example.notesapp.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.core.data.Note
import com.example.core.repository.NoteRepository
import com.example.core.usecases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val repository = NoteRepository(RoomNoteDataSource(application))

    val usecase = UseCase(
        AddNote(repository),
        GetAllNotes(repository),
        GetNotes(repository),
        RemoveNote(repository),
        UpdateNote(repository)
    )
    var fetched = MutableLiveData<List<Note>>()

    fun getNotes() {
        coroutineScope.launch {
            val notes = usecase.getAllNotes()
            fetched.postValue(notes)
        }
    }
}