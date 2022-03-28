package com.osenov.rickandmorty.ui.list_episodes

import com.google.common.truth.Truth.assertThat
import com.osenov.rickandmorty.data.model.EpisodeItem
import com.osenov.rickandmorty.data.model.EpisodeUI
import com.osenov.rickandmorty.data.model.Season
import com.osenov.rickandmorty.data.repository.FakeCharacterRepository
import com.osenov.rickandmorty.domain.GetCharacterEpisodesUseCase
import com.osenov.rickandmorty.util.network.Result
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class EpisodesListViewModelTest {

    private val episodes =
        arrayListOf(Season(1), EpisodeItem(1, "name", "13 dec 2022", "S01E06"))

    private lateinit var viewModel: EpisodesListViewModel

    @Before
    fun setup() {
        viewModel = EpisodesListViewModel(
            arrayListOf("/1", "/2", "/3"),
            GetCharacterEpisodesUseCase(FakeCharacterRepository())
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun viewModelTest() = runTest {
        viewModel.getCharacterEpisodes().collect {
            when (it.status) {
                Result.Status.SUCCESS -> assertThat(it.data).containsAnyIn(episodes)
                Result.Status.ERROR -> assertEquals(it.message, "error")
                Result.Status.LOADING -> assert(true)
            }
        }
    }
}