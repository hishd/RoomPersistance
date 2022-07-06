package com.hishd.roompersistance.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(
    context: Context,
    @DimenRes
    var top: Int,
    @DimenRes
    var bottom: Int,
    @DimenRes
    var left: Int,
    @DimenRes
    var right: Int
    ): RecyclerView.ItemDecoration() {

        init {
            this.top = context.resources.getDimensionPixelSize(top)
            this.bottom = context.resources.getDimensionPixelSize(bottom)
            this.left = context.resources.getDimensionPixelSize(left)
            this.right = context.resources.getDimensionPixelSize(right)
        }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(left, top, right, bottom)
    }
}