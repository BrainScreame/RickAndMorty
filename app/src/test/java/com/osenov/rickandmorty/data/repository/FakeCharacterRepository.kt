package com.osenov.rickandmorty.data.repository

import androidx.paging.PagingSource
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.EpisodeItem
import com.osenov.rickandmorty.data.model.EpisodeUI
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.data.page_source.CharacterPageSource
import com.osenov.rickandmorty.util.addSeasons
import com.osenov.rickandmorty.util.network.Result
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.mockito.Mockito


class FakeCharacterRepository : CharacterRepository {

    private val pageSource: CharacterPageSource.Factory =
        Mockito.mock(CharacterPageSource.Factory::class.java)

    private val episodes =
        arrayListOf<EpisodeUI>(EpisodeItem(1, "name", "13 dec 2022", "S01E06"))

    private val observeCharacter = MutableSharedFlow<Result<Character>>()
    private val observeEpisodes = flow<Result<List<EpisodeUI>>> {
        emit(Result.loading())
        delay(1000)
        emit(Result.error("error", null))
        delay(1000)
        emit(Result.success(episodes.also { it.addSeasons() }))
    }


    override fun queryCharacters(filter: FilterCharacter): PagingSource<Int, Character> {
        return pageSource.create(filter)
    }

    override suspend fun getSingleCharacter(characterId: Long): Flow<Result<Character>> {
        return observeCharacter
    }

    override suspend fun getCharacterEpisodes(episode_ids: List<Int>): Flow<Result<List<EpisodeUI>>> {
        return observeEpisodes
    }
}