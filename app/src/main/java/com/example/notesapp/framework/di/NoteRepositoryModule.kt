package com.example.notesapp.framework.di

import android.app.Application
import com.example.core.repository.NoteRepository
import com.example.notesapp.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class NoteRepositoryModule {
    @Provides
    fun provideRepo(app:Application) = NoteRepository(RoomNoteDataSource(app))
}