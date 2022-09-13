package com.example.notesapp.framework.di

import com.example.notesapp.framework.ListViewModel
import com.example.notesapp.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class,NoteRepositoryModule::class,UseCaseModule::class])
interface viewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}