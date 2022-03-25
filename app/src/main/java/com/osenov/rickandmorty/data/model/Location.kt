package com.osenov.rickandmorty.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    @ColumnInfo(name = "location_name") val name: String,
    @ColumnInfo(name = "location_url")val url: String
) : Parcelable
