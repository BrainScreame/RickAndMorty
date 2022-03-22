package com.osenov.rickandmorty.data.page_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class CharacterPageSource @AssistedInject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
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
        try {
            val numberPage: Int = params.key ?: INITIAL_PAGE_NUMBER
            val response = characterRemoteDataSource.fetchCharacters(numberPage, filter)
            return if (response.isSuccessful) {
                val characterResponse = checkNotNull(response.body())
                val nextKey =
                    if (numberPage < characterResponse.information.pages) numberPage + 1 else null
                val prevKey = if (numberPage == INITIAL_PAGE_NUMBER) null else numberPage - 1
                LoadResult.Page(characterResponse.characters, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("filter") filter: FilterCharacter
        ): CharacterPageSource
    }

}