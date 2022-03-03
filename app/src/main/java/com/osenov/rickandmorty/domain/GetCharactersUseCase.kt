package com.osenov.rickandmorty.domain

import com.osenov.rickandmorty.data.model.CharacterResponse
import com.osenov.rickandmorty.data.repository.CharacterRepository
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    fun execute(numberPage : Int) : Flow<Result<CharacterResponse>> {
        return characterRepository.getCharacters(numberPage)
    }
}