package com.osenov.rickandmorty.ui.list_characters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.osenov.rickandmorty.R
import com.osenov.rickandmorty.databinding.FragmentCharactersListBinding
import com.osenov.rickandmorty.ui.list_characters.list.CharacterAdapter
import com.osenov.rickandmorty.ui.list_characters.list.CharacterItemDecoration
import com.osenov.rickandmorty.util.appComponent
import kotlinx.coroutines.flow.collectLatest
import androidx.paging.CombinedLoadStates
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.osenov.rickandmorty.ui.list_characters.list.CharacterLoaderStateAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.paging.LoadState
import com.osenov.rickandmorty.ui.character_information.CharacterDetailInformationFragment
import androidx.lifecycle.flowWithLifecycle
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.util.safeNavigate
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharactersListFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentCharactersListBinding.inflate(layoutInflater)
    }

    private val viewModel: CharactersListViewModel by activityViewModels() {
        requireContext().appComponent.viewModelsFactory()
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterAdapter { character ->
            if (character != null) {
                findNavController().navigate(
                    R.id.action_charactersListFragment_to_characterDetailInformationFragment,
                    CharacterDetailInformationFragment.makeArgs(character)
                )
            }
        }
    }

    private var searchView: SearchView? = null

    var queryTextChangedJob: Job? = null

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

        val searchItem = binding.characterToolbar.menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        binding.characterToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_filter -> {
                    findNavController().safeNavigate(
                        R.id.charactersListFragment, R.id.bottomFilterFragment2
                    )
                    true
                }
                R.id.action_search -> {
                    addOnQueryTextListenerOnSearchView()
                    true
                }
                else -> false
            }
        }

        viewModel.filter
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(::updateSearchFilter)
            .launchIn(lifecycleScope)

        show()

    }

    private fun addOnQueryTextListenerOnSearchView() {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                queryTextChangedJob?.cancel()

                queryTextChangedJob = lifecycleScope.launchWhenResumed {
                    delay(700)
                    viewModel.setFilter(
                        FilterCharacter(
                            newText ?: "",
                            viewModel.filter.value.status,
                            viewModel.filter.value.gender
                        )
                    )
                }

                return false
            }
        })
    }

    private fun updateSearchFilter(filter: FilterCharacter) {
        if (filter.status.isNotEmpty() || filter.gender.isNotEmpty()) {
            binding.characterToolbar.menu.findItem(R.id.action_filter).icon.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.teal_700),
                    PorterDuff.Mode.SRC_IN
                )
        } else {
            binding.characterToolbar.menu.findItem(R.id.action_filter).icon.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.gray_400),
                    PorterDuff.Mode.SRC_IN
                )
        }
        searchView?.let {
            addOnQueryTextListenerOnSearchView()
            if ((it.query ?: "") != filter.query) {
                it.setQuery(filter.query, false)
            }
        }

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
                binding.errorLayout.errorMessage.text =
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