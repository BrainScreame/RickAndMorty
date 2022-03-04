package com.osenov.rickandmorty.ui.list_episodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.osenov.rickandmorty.R

class EpisodesListFragment : Fragment() {

    companion object {
        const val characterDetail = "CHARACTER_DETAIL"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episodes_list, container, false)
    }

}