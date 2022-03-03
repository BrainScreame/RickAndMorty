package com.osenov.rickandmorty.ui.list_characters.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osenov.rickandmorty.databinding.ItemLoadStateBinding
import androidx.core.view.isVisible

class CharacterLoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), retry
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


}

class LoadStateViewHolder(
    private val viewBinding: ItemLoadStateBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(loadState: LoadState) {
        with(viewBinding) {
            if (loadState is LoadState.Error) {
                loadStateErrorMessage.text = loadState.error.localizedMessage
            }
            loadStateProgress.isVisible = loadState is LoadState.Loading
            loadStateRetry.isVisible = loadState !is LoadState.Loading
            loadStateErrorMessage.isVisible = loadState !is LoadState.Loading
            loadStateRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }

}