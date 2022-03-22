package com.osenov.rickandmorty.ui.list_episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.EpisodeUI
import com.osenov.rickandmorty.domain.GetCharacterEpisodesUseCase
import com.osenov.rickandmorty.util.network.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class EpisodesListViewModel(
    private val stringsEpisodesUrl: ArrayList<String>,
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase
) : ViewModel() {

//    val episodes: Flow<Result<ArrayList<EpisodeUI>>> =
//        getCharacterEpisodesUseCase.execute(stringsEpisodesUrl)

    suspend fun getCharacterEpisodes(): Flow<Result<ArrayList<EpisodeUI>>> {
        return getCharacterEpisodesUseCase.execute(stringsEpisodesUrl)
    }

    class EpisodesListViewModelFactory @AssistedInject constructor(
        @Assisted("CHARACTER_DETAIL") private val stringsEpisodesUrl: ArrayList<String>,
        private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EpisodesListViewModel(stringsEpisodesUrl, getCharacterEpisodesUseCase) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted("CHARACTER_DETAIL") stringsEpisodesUrl: ArrayList<String>): EpisodesListViewModelFactory
        }

    }

}