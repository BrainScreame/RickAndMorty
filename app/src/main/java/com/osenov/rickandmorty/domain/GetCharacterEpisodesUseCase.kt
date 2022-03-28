package com.osenov.rickandmorty.domain

import com.osenov.rickandmorty.data.model.EpisodeUI
import com.osenov.rickandmorty.data.repository.CharacterRepository
import com.osenov.rickandmorty.data.repository.DefaultCharacterRepository
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterEpisodesUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun execute(urlEpisodes: List<String>): Flow<Result<List<EpisodeUI>>> {
        val episodeIds = urlEpisodes.map { s ->
            var ind = s.length - 1
            while (s[ind].isDigit()) ind--
            s.substring(ind + 1).toInt()
        }

        return characterRepository.getCharacterEpisodes(episodeIds)
    }
}