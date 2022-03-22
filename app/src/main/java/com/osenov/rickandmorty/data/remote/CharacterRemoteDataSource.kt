package com.osenov.rickandmorty.data.remote

import com.osenov.rickandmorty.data.model.FilterCharacter
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val characterService: CharacterService) {

    suspend fun fetchCharacters(numberPage: Int, filter: FilterCharacter) =
        characterService.getCharacters(numberPage, filter.query, filter.status, filter.gender)

    suspend fun fetchSingleCharacter(characterId: Long) =
        characterService.getSingleCharacter(characterId)

    suspend fun fetchCharacterEpisodes(episode_ids: List<Int>) =
        characterService.getCharacterEpisodes(episode_ids)
}