package com.osenov.rickandmorty.data.remote

import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val characterService: CharacterService) {

    suspend fun fetchCharacters(numberPage : Int) = characterService.getCharacters(numberPage)

}