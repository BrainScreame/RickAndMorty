package com.osenov.rickandmorty.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.osenov.rickandmorty.data.model.Location
import com.osenov.rickandmorty.data.model.Origin
import com.osenov.rickandmorty.data.model.room.CharacterEntity
import com.osenov.rickandmorty.data.model.room.EpisodeEntity
import com.osenov.rickandmorty.data.model.room.EpisodeUrlEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CharacterDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RickAndMortyDatabase
    private lateinit var characterDao: CharacterDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RickAndMortyDatabase::class.java
        ).allowMainThreadQueries().build()
        characterDao = database.characterDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCharactersWithEpisodes() = runTest {
        val characterEntity = CharacterEntity(
            1111,
            "serj",
            "alive",
            "",
            "human",
            "male",
            Origin("", ""),
            Location("", ""),
            "",
            "",
            "04 april 2000"
        )
        val episodeUrlEntities = listOf(
            EpisodeUrlEntity(1, characterEntity.id, "1"),
            EpisodeUrlEntity(2, characterEntity.id, "2")
        )
        characterDao.insertCharacters(listOf(characterEntity))
        characterDao.insertCharacterEpisodes(episodeUrlEntities)

        val characters = characterDao.getCharacters(1, "serj", "alive", "male")

        assertThat(characters.map { it.character }).contains(characterEntity)
        assertThat(characters.map { it.episodesUrl }).contains(episodeUrlEntities)
    }


    @Test
    fun insertEpisodes() = runTest {
        val episodesInsert = listOf(
            EpisodeEntity(3, "1 ser", "28 aug 2022", "1e23"),
            EpisodeEntity(4, "2 eps", "18 sep 2020", "34p2d3")
        )
        characterDao.insertEpisodes(episodesInsert)

        val episodes = characterDao.getCharacterEpisodes(listOf(3, 4))

        assertThat(episodes).containsAnyIn(episodesInsert)
    }

}