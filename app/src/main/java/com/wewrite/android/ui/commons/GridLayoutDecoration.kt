package com.wewrite.android.ui.commons

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HomeGroupGridDecoration(private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)

        // 첫번째 아이템 왼쪽 margin
        if (position == 0) {
            outRect.left = 32
            outRect.right = space
        }
        // 각 아이템 사이 간격
        if (position != 0) {
            outRect.left = space
            outRect.right = space
        }
        //마지막 아이템 오른쪽 margin
        if (position == state.itemCount - 1) {
            outRect.left = space
            outRect.right = 32
        }
    }
}

class PostGridDecoration(private val space: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) { // 각 아이템 사이 간격
        outRect.bottom = space * 4
    }
}
