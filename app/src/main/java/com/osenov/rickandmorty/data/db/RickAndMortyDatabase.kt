package com.osenov.rickandmorty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.osenov.rickandmorty.data.db.RickAndMortyDatabase.Companion.DB_VERSION
import com.osenov.rickandmorty.data.model.room.CharacterEntity
import com.osenov.rickandmorty.data.model.room.EpisodeEntity
import com.osenov.rickandmorty.data.model.room.EpisodeUrlEntity

@Database(entities = [CharacterEntity::class, EpisodeUrlEntity::class, EpisodeEntity::class], version = DB_VERSION)
abstract class RickAndMortyDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "weather.db"
        const val DB_VERSION = 1
    }

    abstract fun characterDao(): CharacterDao

}