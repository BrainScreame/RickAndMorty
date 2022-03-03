package com.osenov.rickandmorty.ui.list_characters.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharacterItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val params = view.layoutParams as GridLayoutManager.LayoutParams
        val position: Int = parent.getChildAdapterPosition(view)

        with(outRect) {
            if (params.spanIndex % 2 == 0) {
                top = space
                left = space
                right = space / 2
            } else {
                top = space
                left = space / 2
                right = space
            }

            if (state.itemCount % 2 == 0 && (position == state.itemCount - 1 || position == state.itemCount - 2)) {
                bottom = space
            } else if (state.itemCount % 2 == 1 && position == state.itemCount - 1) {
                bottom = space
            }

        }
    }
}