package com.osenov.rickandmorty.ui.list_episodes.adaprer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.osenov.rickandmorty.data.model.EpisodeItem
import com.osenov.rickandmorty.data.model.EpisodeUI
import com.osenov.rickandmorty.data.model.Season
import com.osenov.rickandmorty.databinding.ItemEpisodeBinding
import com.osenov.rickandmorty.databinding.ItemSeasonBinding

class EpisodesAdapter(private val onItemClicked: (EpisodeUI?) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val EPISODE = 1
        private const val SEASON = 2
    }

    private var episodes = ArrayList<EpisodeUI>()

    fun setData(data: List<EpisodeUI>) {
        episodes.clear()
        this.episodes.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            EPISODE -> EpisodeViewHolder(ItemEpisodeBinding.inflate(inflater, parent, false))
            else -> SeasonViewHolder(ItemSeasonBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SeasonViewHolder -> holder.bind(episodes[position] as Season)
            is EpisodeViewHolder -> holder.bind(episodes[position] as EpisodeItem)
        }
        holder.itemView.setOnClickListener {
            onItemClicked(episodes[position])
        }
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (episodes[position]) {
            is EpisodeItem -> EPISODE
            is Season -> SEASON
        }
    }

}
