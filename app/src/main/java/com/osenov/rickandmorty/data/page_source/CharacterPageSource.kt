package com.osenov.rickandmorty.data.page_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.osenov.rickandmorty.data.db.CharacterDao
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.lang.Exception
import java.net.UnknownHostException

class CharacterPageSource @AssistedInject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterDao: CharacterDao,
    @Assisted("filter") private val filter: FilterCharacter
) : PagingSource<Int, Character>() {

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val numberPage: Int = params.key ?: INITIAL_PAGE_NUMBER
        val prevKey = if (numberPage == INITIAL_PAGE_NUMBER) null else numberPage - 1

        return try {
            val response = characterRemoteDataSource.fetchCharacters(numberPage, filter)
            val data = checkNotNull(response.body())
            characterDao.insertCharacters(data.characters.map { it.toCharacterEntity() })
            data.characters.forEach {
                characterDao.insertCharacterEpisodes(it.toEpisodeUrlEntity())
            }
            val nextKey = if (data.information.pages == numberPage) null else numberPage + 1
            LoadResult.Page(data.characters, prevKey, nextKey)

        } catch (e: UnknownHostException) {
            getCharactersFromDB(numberPage, prevKey, e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    private suspend fun getCharactersFromDB(
        numberPage: Int,
        prevKey: Int?,
        e: UnknownHostException
    ): LoadResult<Int, Character> {
        val characters =
            characterDao.getCharacters(
                numberPage,
                "%${filter.query}%",
                "%${filter.status}%",
                "%${filter.gender}%"
            )
                .map { charactersWithEpisodesUrl ->
                    charactersWithEpisodesUrl.toCharacter()
                }

        val nextKey = if (characters.size < 20) null else numberPage + 1
        return if (characters.isEmpty()) {
            LoadResult.Error(e)
        } else {
            LoadResult.Page(characters, prevKey, nextKey)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("filter") filter: FilterCharacter
        ): CharacterPageSource
    }

}