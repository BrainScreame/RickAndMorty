package com.osenov.rickandmorty.data.db

import androidx.room.*
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.data.model.room.CharacterEntity
import com.osenov.rickandmorty.data.model.room.CharacterWithEpisodesUrl
import com.osenov.rickandmorty.data.model.room.EpisodeEntity
import com.osenov.rickandmorty.data.model.room.EpisodeUrlEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterEpisodes(episodes: List<EpisodeUrlEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodes WHERE id IN (:episode_ids)")
    suspend fun getCharacterEpisodes(episode_ids: List<Int>) : List<EpisodeEntity>

    @Transaction
    @Query("SELECT * FROM characters WHERE name LIKE :query AND status LIKE :status AND gender LIKE :gender LIMIT 20 OFFSET (:numberPage - 1) * 20")
    suspend fun getCharacters(
        numberPage: Int,
        query: String,
        status: String,
        gender: String
    ): List<CharacterWithEpisodesUrl>

}