package ru.yweber.flaskdionysus.core.decorator

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

/**
 * Created on 01.04.2020
 * @author YWeber */

class PaddingItemDecorator(private val spacing: Int = 8) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.top = spacing
        outRect.bottom = spacing
        outRect.left = spacing * 2
        outRect.right = spacing * 2
    }

}