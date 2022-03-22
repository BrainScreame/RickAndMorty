package com.osenov.rickandmorty.ui.list_episodes.adaprer

import androidx.recyclerview.widget.RecyclerView
import com.osenov.rickandmorty.R
import com.osenov.rickandmorty.data.model.EpisodeItem
import com.osenov.rickandmorty.data.model.Season
import com.osenov.rickandmorty.databinding.ItemEpisodeBinding
import com.osenov.rickandmorty.databinding.ItemSeasonBinding

class EpisodeViewHolder(private val binding: ItemEpisodeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(episode: EpisodeItem) {
        with(binding) {
            textEpisodeName.text = episode.name
            textEpisode.text = episode.episode
            textAirDate.text = episode.airDate
        }
    }
}

class SeasonViewHolder(private val binding: ItemSeasonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(season: Season) {
        binding.textEpisodes.text =
            binding.root.resources.getString(R.string.season, season.number)
    }

}