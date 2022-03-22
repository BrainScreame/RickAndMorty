package com.osenov.rickandmorty.ui.list_episodes

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.osenov.rickandmorty.R
import com.osenov.rickandmorty.util.appComponent
import androidx.recyclerview.widget.DividerItemDecoration
import com.osenov.rickandmorty.databinding.FragmentEpisodesListBinding
import com.osenov.rickandmorty.ui.list_episodes.adaprer.EpisodesAdapter
import com.osenov.rickandmorty.util.network.Result
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.osenov.rickandmorty.data.model.*
import com.osenov.rickandmorty.util.listStringsArgs
import javax.inject.Inject
import kotlin.collections.ArrayList

class EpisodesListFragment : Fragment() {

    companion object {
        const val CHARACTER_EPISODES = "CHARACTER_DETAIL"

        fun makeArgs(episodesUrl: ArrayList<String>): Bundle {
            return Bundle(1).apply {
                putStringArrayList(CHARACTER_EPISODES, episodesUrl)
            }
        }
    }

    private val stringsEpisodesUrl: ArrayList<String> by listStringsArgs(CHARACTER_EPISODES)

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentEpisodesListBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var factory: EpisodesListViewModel.EpisodesListViewModelFactory.Factory

    private val viewModel: EpisodesListViewModel by viewModels {
        factory.create(stringsEpisodesUrl)
    }

    private val episodesAdapter = EpisodesAdapter {
        when (it) {
            is EpisodeItem -> Toast.makeText(requireContext(), it.name, Toast.LENGTH_LONG).show()
            is Season -> Toast.makeText(
                requireContext(),
                resources.getString(R.string.season, it.number),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val dividerItemDecoration by lazy(LazyThreadSafetyMode.NONE) {
        DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewEpisodes.startShimmerAnimation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.episodesToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        observeEpisodes()

        binding.swipeToRefreshEpisodes.setOnRefreshListener {
            observeEpisodes()
            binding.swipeToRefreshEpisodes.isRefreshing = false
        }

    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewEpisodes.stopShimmerAnimation()
    }

    private fun observeEpisodes() {
        lifecycleScope.launchWhenStarted {
            viewModel.getCharacterEpisodes().collect {
                when (it.status) {
                    Result.Status.LOADING -> showStatusLoad()
                    Result.Status.SUCCESS -> showEpisodes(it.data)
                    Result.Status.ERROR -> showStatusError(it.message)
                }
            }
        }
    }

    private fun showStatusLoad() {
        with(binding) {
            recyclerEpisodes.isVisible = false
            errorLayoutEpisodes.root.isVisible = false
            shimmerViewEpisodes.isVisible = true
        }
    }

    private fun showStatusError(message: String?) {
        with(binding) {
            recyclerEpisodes.isVisible = false
            shimmerViewEpisodes.isVisible = false
            errorLayoutEpisodes.root.isVisible = true

            errorLayoutEpisodes.errorMessage.text = message

            errorLayoutEpisodes.retry.setOnClickListener {
                observeEpisodes()
            }
        }
    }

    private fun showEpisodes(episodes: ArrayList<EpisodeUI>?) {
        with(binding) {
            recyclerEpisodes.isVisible = true
            shimmerViewEpisodes.isVisible = false
            errorLayoutEpisodes.root.isVisible = false

            recyclerEpisodes.adapter = episodesAdapter
            recyclerEpisodes.addItemDecoration(dividerItemDecoration)
            if (episodes != null) {
                episodesAdapter.setData(episodes)
            }

        }
    }

}