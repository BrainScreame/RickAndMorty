package com.osenov.rickandmorty.ui.character_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.domain.GetCharacterEpisodesUseCase
import com.osenov.rickandmorty.domain.GetSingleCharacterUseCase
import com.osenov.rickandmorty.ui.list_episodes.EpisodesListViewModel
import com.osenov.rickandmorty.util.network.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow

class CharacterDetailInformationViewModel(
    private val character: Character,
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase
) : ViewModel() {

    suspend fun updateCharacter(): Flow<Result<Character>> {
        return getSingleCharacterUseCase.execute(character.id)
    }

    class CharacterDetailInformationViewModelFactory @AssistedInject constructor(
        @Assisted("CHARACTER") private val character: Character,
        private val getSingleCharacterUseCase: GetSingleCharacterUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CharacterDetailInformationViewModel(character, getSingleCharacterUseCase) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted("CHARACTER") character: Character): CharacterDetailInformationViewModelFactory
        }

    }

}