package com.osenov.rickandmorty.ui.character_information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.osenov.rickandmorty.R


class CharacterDetailInformationFragment : Fragment() {

    companion object {
        const val characterDetail = "CHARACTER_DETAIL"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail_information, container, false)
    }

}