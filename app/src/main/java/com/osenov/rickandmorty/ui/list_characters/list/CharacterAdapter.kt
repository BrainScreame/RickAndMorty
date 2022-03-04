package com.osenov.rickandmorty.ui.list_characters.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.databinding.ItemCharacterBinding

class CharacterAdapter(private val onItemClicked: (Character?) -> Unit) :
    PagingDataAdapter<Character, CharacterViewHolder>(CharacterDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemClicked(getItem(position)) }
    }
}

private object CharacterDiffItemCallback : DiffUtil.ItemCallback<Character>() {

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }
}