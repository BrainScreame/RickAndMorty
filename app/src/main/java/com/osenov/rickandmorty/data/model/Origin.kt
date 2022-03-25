package com.osenov.rickandmorty.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Origin(
    @ColumnInfo(name = "origin_name") val name: String,
    @ColumnInfo(name = "origin_url") val url: String
) : Parcelable