package com.osenov.rickandmorty.ui.character_information

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.osenov.rickandmorty.R
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.databinding.FragmentCharacterDetailInformationBinding
import com.osenov.rickandmorty.util.appComponent
import com.osenov.rickandmorty.util.network.Result
import com.osenov.rickandmorty.ui.list_episodes.EpisodesListFragment
import com.osenov.rickandmorty.ui.list_episodes.EpisodesListViewModel
import com.osenov.rickandmorty.util.characterArgs
import com.osenov.rickandmorty.util.listStringsArgs
import javax.inject.Inject


class CharacterDetailInformationFragment : Fragment() {

    companion object {
        const val CHARACTER = "CHARACTER"

        fun makeArgs(character: Character): Bundle {
            return Bundle(1).apply {
                putParcelable(CHARACTER, character)
            }
        }
    }

    private val character: Character by characterArgs(CHARACTER)

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentCharacterDetailInformationBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var factory: CharacterDetailInformationViewModel.CharacterDetailInformationViewModelFactory.Factory

    private val viewModel: CharacterDetailInformationViewModel by viewModels {
        factory.create(character)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshCharacter()
        showCharacterInfo(character)
        binding.swipeToRefreshCharacterItem.setOnRefreshListener {
            refreshCharacter()
        }

    }

    private fun refreshCharacter() {
        lifecycleScope.launchWhenStarted {
            viewModel.updateCharacter().collect {
                when (it.status) {
                    Result.Status.LOADING -> binding.swipeToRefreshCharacterItem.isRefreshing = true
                    Result.Status.SUCCESS -> {
                        if (it.data != null) {
                            showCharacterInfo(it.data)
                        }
                    }
                    Result.Status.ERROR -> showError(it.error?.message)
                }
            }
        }
    }

    private fun showError(message: String?) {
        binding.swipeToRefreshCharacterItem.isRefreshing = false
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.error_update_message).plus(message),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showCharacterInfo(character: Character) {
        binding.swipeToRefreshCharacterItem.isRefreshing = false
        with(binding) {
            toolbarCharacterInfo.title = character.name
            toolbarCharacterInfo.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }

            Glide.with(requireContext()).load(character.imageUrl).placeholder(R.drawable.load)
                .centerCrop()
                .into(imageCharacter)

            textCharacterStatus.text = character.status
            textCharacterName.text = character.name
            textSpecies.text = character.species.uppercase()

            genderInfo.textTitle.text = resources.getString(R.string.gender_text)
            genderInfo.textSubTitle.text = character.gender

            originInfo.textTitle.text = resources.getString(R.string.origin_text)
            originInfo.textSubTitle.text = character.origin.name

            typeInfo.textTitle.text = resources.getString(R.string.type_text)
            typeInfo.textSubTitle.text =
                if (character.type != "") character.type else resources.getString(R.string.unknown_text)

            locationInfo.textTitle.text = resources.getString(R.string.location_text)
            locationInfo.textSubTitle.text = character.location.name

            layoutEpisodes.setOnClickListener {
                findNavController().navigate(
                    R.id.action_characterDetailInformationFragment_to_episodesListFragment,
                    EpisodesListFragment.makeArgs(this@CharacterDetailInformationFragment.character.episodes)
                )
            }

        }

    }
}

