package com.osenov.rickandmorty.ui.list_characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.osenov.rickandmorty.R
import com.osenov.rickandmorty.databinding.FragmentCharactersListBinding
import com.osenov.rickandmorty.ui.list_characters.list.CharacterAdapter
import com.osenov.rickandmorty.ui.list_characters.list.CharacterItemDecoration
import com.osenov.rickandmorty.util.appComponent
import kotlinx.coroutines.flow.collectLatest
import androidx.core.os.bundleOf
import androidx.paging.CombinedLoadStates
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.osenov.rickandmorty.ui.character_information.CharacterDetailInformationFragment.Companion.characterDetail
import com.osenov.rickandmorty.ui.list_characters.list.CharacterLoaderStateAdapter
import androidx.core.view.isVisible
import androidx.paging.LoadState

class CharactersListFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentCharactersListBinding.inflate(layoutInflater)
    }

    private val viewModel: CharactersListViewModel by viewModels {
        requireContext().appComponent.viewModelsFactory()
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterAdapter { character ->
            findNavController().navigate(
                R.id.action_charactersListFragment_to_characterDetailInformationFragment,
                bundleOf(characterDetail to character)
            )
        }
    }
    private val itemDecoration by lazy(LazyThreadSafetyMode.NONE) {
        CharacterItemDecoration(resources.getDimensionPixelSize(R.dimen.character_recycler_offset))
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        show()

    }

    private fun show() {
        //LayoutManager
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount) 2 else 1
            }
        }
        binding.recyclerCharacters.layoutManager = gridLayoutManager

        //ItemDecoration
        binding.recyclerCharacters.addItemDecoration(itemDecoration)

        //Submit Data
        lifecycleScope.launchWhenStarted {
            viewModel.characters.collectLatest(adapter::submitData)
        }

        //Handled LoadStates
        binding.recyclerCharacters.adapter = adapter.withLoadStateHeaderAndFooter(
            header = CharacterLoaderStateAdapter { (adapter.retry()) },
            footer = CharacterLoaderStateAdapter { (adapter.retry()) }
        )

        //State Listener
        adapter.addLoadStateListener { state: CombinedLoadStates ->
            binding.recyclerCharacters.isVisible =
                state.refresh != LoadState.Loading && state.refresh !is LoadState.Error
            binding.shimmerViewContainer.isVisible = state.refresh == LoadState.Loading
            binding.errorLayout.root.isVisible = state.refresh is LoadState.Error
            if (state.refresh is LoadState.Error) {
                binding.errorLayout.errorMassage.text =
                    (state.refresh as LoadState.Error).error.message
                binding.errorLayout.retry.setOnClickListener {
                    adapter.refresh()
                }

            }
        }

        // Swipe refresh Layout
        binding.swipeToRefreshCharacters.setOnRefreshListener {
            adapter.refresh()
            binding.swipeToRefreshCharacters.isRefreshing = false
        }
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmerAnimation()
        binding.recyclerCharacters.removeItemDecoration(itemDecoration)
    }

}