package ru.yweber.flaskdionysus.core.view.scroll

import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * Created on 09.04.2020
 * @author YWeber */
private const val THRESHOLD = 800

class HideFragmentScrollListener(
    val hide: () -> Unit,
    val visible: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var distanceHide = 0
    private var distanceVisible = 0
    private var controlsVisible = true
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        Timber.e(recyclerView.getChildAt(0).measuredHeight.toString())
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        distanceVisible += dy
        if (distanceHide > THRESHOLD && controlsVisible) {
            hide()
            controlsVisible = false
            distanceHide = 0
        } else if (distanceVisible < THRESHOLD / 2 && !controlsVisible) {
            visible()
            controlsVisible = true
            distanceHide = 0
        }
        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            distanceHide += dy
        }
    }
}