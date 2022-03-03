package com.osenov.rickandmorty.ui.list_characters.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osenov.rickandmorty.R
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.databinding.ItemCharacterBinding

class CharacterViewHolder(private val viewBinding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(character: Character?) {
        with(viewBinding) {

            Glide.with(root).load(character?.imageUrl).placeholder(R.drawable.load).centerCrop()
                .into(imageCharacter)

            textCharacterStatus.text = character?.status
            textCharacterName.text = character?.name
        }
    }

}
