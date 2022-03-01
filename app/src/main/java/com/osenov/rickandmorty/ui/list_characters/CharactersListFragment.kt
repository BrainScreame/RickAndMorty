package com.osenov.rickandmorty.ui.list_characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.osenov.rickandmorty.databinding.FragmentCharactersListBinding
import com.osenov.rickandmorty.util.appComponent

class CharactersListFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentCharactersListBinding.inflate(layoutInflater)
    }

    private val viewModel: CharactersListViewModel by viewModels {
        requireContext().appComponent.viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}