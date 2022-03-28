package com.osenov.rickandmorty.util

import com.osenov.rickandmorty.data.model.EpisodeItem
import com.osenov.rickandmorty.data.model.EpisodeUI
import com.osenov.rickandmorty.data.model.Season
import org.junit.Assert.assertArrayEquals
import org.junit.Test

class ExtensionsTest {
    @Test
    fun getEpisodesWithSeasons() {
        val episodes = arrayListOf<EpisodeUI>(
            EpisodeItem(
                1,
                "The Ricklantis Mixup",
                "September 10, 2017",
                "S03E07"
            ), EpisodeItem(
                2,
                "The ...",
                "November 11, 2022",
                "S11E17"
            )
        )

        episodes.addSeasons()

        val episodesWithSeason = arrayListOf<EpisodeUI>(
            Season(3),
            EpisodeItem(
                1,
                "The Ricklantis Mixup",
                "September 10, 2017",
                "S03E07"
            ),
            Season(11),
            EpisodeItem(
                2,
                "The ...",
                "November 11, 2022",
                "S11E17"
            )
        )
        assertArrayEquals(episodes.toArray(), episodesWithSeason.toArray())
    }
}