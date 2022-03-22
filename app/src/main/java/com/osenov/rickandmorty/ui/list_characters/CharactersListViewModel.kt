package com.osenov.rickandmorty.ui.list_characters


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.domain.GetQueryCharactersUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class CharactersListViewModel @Inject constructor(
    private val getQueryCharactersUseCase: Provider<GetQueryCharactersUseCase>
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 20 // it's const number which server response for require
        private const val DEFAULT_STATE = ""
    }

    private val _filter =
        MutableStateFlow(FilterCharacter(DEFAULT_STATE, DEFAULT_STATE, DEFAULT_STATE))
    val filter: StateFlow<FilterCharacter> = _filter.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    val characters: StateFlow<PagingData<Character>> = filter
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    private fun newPager(filterCharacter: FilterCharacter): Pager<Int, Character> {
        return Pager(PagingConfig(PAGE_SIZE, enablePlaceholders = false)) {
            val queryNewsUseCase = getQueryCharactersUseCase.get()
            queryNewsUseCase(
                filterCharacter
            ).also { newPagingSource = it }
        }
    }

    fun setFilter(filter: FilterCharacter) {
        _filter.tryEmit(filter)
    }


}