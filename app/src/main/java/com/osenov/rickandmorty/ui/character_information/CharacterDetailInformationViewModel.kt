package com.osenov.rickandmorty.ui.character_information

import androidx.lifecycle.ViewModel
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.domain.GetSingleCharacterUseCase
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.flow.Flow

class CharacterDetailInformationViewModel(
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase
) : ViewModel() {

    suspend fun updateCharacter(characterId : Long): Flow<Result<Character>> {
        return getSingleCharacterUseCase.execute(characterId)
    }

}