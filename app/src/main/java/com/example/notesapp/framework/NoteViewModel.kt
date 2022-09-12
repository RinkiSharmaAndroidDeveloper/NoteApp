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

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val repository = NoteRepository(RoomNoteDataSource(application))

    val usecase = UseCase(
        AddNote(repository),
        GetAllNotes(repository),
        GetNotes(repository),
        RemoveNote(repository),
        UpdateNote(repository)
    )
    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Boolean>()
    fun saveNote(note:Note){
        coroutineScope.launch {
            usecase.addNote(note)
            saved.postValue(true)
        }
    }

    fun updateCrrentNote(note:Note){
        coroutineScope.launch {
            val note = usecase.updateNote(note)
            currentNote.postValue(true)
        }
    }

    fun deleteNote(note:Note){
        coroutineScope.launch {
            usecase.removeNote(note)
            saved.postValue(true)
        }
    }
}