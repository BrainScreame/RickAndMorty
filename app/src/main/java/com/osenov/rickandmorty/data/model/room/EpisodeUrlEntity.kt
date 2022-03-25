package com.osenov.rickandmorty.data.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_episodes_url")
data class EpisodeUrlEntity(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val characterId : Long,
    val episodeUrl: String
)