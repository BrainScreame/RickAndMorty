package com.osenov.rickandmorty.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.osenov.rickandmorty.data.db.CharacterDao
import com.osenov.rickandmorty.data.db.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        context: Context
    ): RickAndMortyDatabase {
        return Room.databaseBuilder(context, RickAndMortyDatabase::class.java, RickAndMortyDatabase.DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(rickAndMortyDatabase: RickAndMortyDatabase): CharacterDao {
        return rickAndMortyDatabase.characterDao()
    }

}