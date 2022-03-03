package com.osenov.rickandmorty.ui.list_characters


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.osenov.rickandmorty.data.page_source.CharacterPageSource
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 20 // it's const number which server response for require
    }

    val characters = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            CharacterPageSource(characterRemoteDataSource)
        }.flow
            .cachedIn(viewModelScope)

}