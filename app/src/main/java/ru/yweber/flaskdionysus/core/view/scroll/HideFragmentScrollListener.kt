package ru.yweber.flaskdionysus.core.view.scroll

import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import kotlin.math.abs

/**
 * Created on 09.04.2020
 * @author YWeber */
private const val THRESHOLD = 400

class HideFragmentScrollListener(
    val hide: () -> Unit,
    val visible: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var distance = 0
    private var controlsVisible = true
    private var distanceTest = 0


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        distanceTest += dy
        Timber.e("dy:$dy distance:$distanceTest")
        if (distance > THRESHOLD && controlsVisible) {
            hide()
            controlsVisible = false
            distance = 0
        } else if (distanceTest < THRESHOLD && !controlsVisible) {
            visible()
            controlsVisible = true
            distance = 0
        }
        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            distance += dy
        }
    }
}