package com.osenov.rickandmorty.data.model.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.osenov.rickandmorty.data.model.Location
import com.osenov.rickandmorty.data.model.Origin
import kotlinx.parcelize.Parcelize

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    @Embedded val origin: Origin,
    @Embedded val location: Location,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    val url: String,
    val created: String
)
