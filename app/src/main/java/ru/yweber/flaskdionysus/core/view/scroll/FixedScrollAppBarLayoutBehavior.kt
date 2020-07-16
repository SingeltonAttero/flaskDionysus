package ru.yweber.flaskdionysus.core.view.scroll

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout

/**
 * Created on 10.04.2020
 * @author YWeber */

class FixedScrollAppBarLayoutBehavior(context: Context, attrs: AttributeSet) : AppBarLayout.Behavior(context, attrs) {
    init {
        setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean = false
        })
    }
}