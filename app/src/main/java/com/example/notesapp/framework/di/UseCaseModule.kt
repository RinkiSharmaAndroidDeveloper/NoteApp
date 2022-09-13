package com.example.notesapp.framework.di

import com.example.core.repository.NoteRepository
import com.example.core.usecases.*
import com.example.notesapp.framework.UseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideUseCases(noteRepository: NoteRepository) = UseCase(
        AddNote(noteRepository),
        GetAllNotes(noteRepository),
        GetNotes(noteRepository),
        RemoveNote(noteRepository),
        UpdateNote(noteRepository),
        GetWordCount()
    )
}