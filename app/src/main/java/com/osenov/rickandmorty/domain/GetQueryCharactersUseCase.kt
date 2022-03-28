package com.osenov.rickandmorty.domain

import androidx.paging.PagingSource
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.data.repository.CharacterRepository
import com.osenov.rickandmorty.data.repository.DefaultCharacterRepository
import javax.inject.Inject

class GetQueryCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    operator fun invoke(filter: FilterCharacter) : PagingSource<Int, Character> {
        return repository.queryCharacters(filter)
    }
}