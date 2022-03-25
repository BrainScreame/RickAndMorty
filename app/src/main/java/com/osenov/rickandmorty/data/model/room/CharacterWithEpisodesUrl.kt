package com.osenov.rickandmorty.data.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.osenov.rickandmorty.data.model.Character

data class CharacterWithEpisodesUrl(

    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val episodesUrl: List<EpisodeUrlEntity>
) {
    fun toCharacter() = Character(
        character.id,
        character.name,
        character.status,
        character.species,
        character.type,
        character.gender,
        character.origin,
        character.location,
        character.imageUrl,
        ArrayList(episodesUrl.map {
            it.episodeUrl
        }),
        character.url,
        character.created
    )
}