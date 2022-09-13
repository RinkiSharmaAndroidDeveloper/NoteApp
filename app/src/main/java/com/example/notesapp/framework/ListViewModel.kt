package com.example.notesapp.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.core.data.Note

import com.example.core.usecases.*
import com.example.notesapp.framework.di.ApplicationModule
import com.example.notesapp.framework.di.DaggerviewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    @Inject
    lateinit var usecase : UseCase

    init {
        DaggerviewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }
    var fetched = MutableLiveData<List<Note>>()

    fun getNotes() {
        coroutineScope.launch {
            val notes = usecase.getAllNotes()
            notes.forEach{
                it.wordCount = usecase.wordCount.invoke(it)
            }
            fetched.postValue(notes)
        }
    }
}