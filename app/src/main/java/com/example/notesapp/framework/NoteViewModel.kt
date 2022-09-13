package com.example.notesapp.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.core.data.Note


import com.example.notesapp.framework.di.ApplicationModule
import com.example.notesapp.framework.di.DaggerviewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var usecase : UseCase

    init {
        DaggerviewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }
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