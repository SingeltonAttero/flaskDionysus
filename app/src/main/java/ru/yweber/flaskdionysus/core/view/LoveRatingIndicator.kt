package ru.yweber.flaskdionysus.core.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import ru.yweber.flaskdionysus.R

/**
 * Created on 02.04.2020
 * @author YWeber */

class LoveRatingIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    private val listView = mutableListOf<ImageView>()
    private val count: Int
    private val fillingDrawable: Int
    private val emptyDrawable: Int
    private val progress: Int

    init {
        val obtainAttr = context.obtainStyledAttributes(attrs, R.styleable.LoveRatingIndicator, 0, 0)
        count = obtainAttr.getInteger(R.styleable.LoveRatingIndicator_lriv_count_indicator, 5)
        fillingDrawable = obtainAttr.getResourceId(
            R.styleable.LoveRatingIndicator_lriv_filling_drawable,
            R.drawable.ic_favorite_visible
        )
        emptyDrawable = obtainAttr.getResourceId(
            R.styleable.LoveRatingIndicator_lriv_empty_drawable,
            R.drawable.ic_favorite_invisible
        )
        progress = obtainAttr.getInteger(R.styleable.LoveRatingIndicator_lriv_count_progress, 0)
        obtainAttr.recycle()
        val paramsIndicator = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        createIndicator(context, paramsIndicator)
        correctSizeIndicator()
    }

    private fun createIndicator(
        context: Context,
        paramsIndicator: LayoutParams
    ) = (1..count).forEach {
        val indicator = ImageView(context)
        indicator.layoutParams = paramsIndicator
        if (it <= progress) {
            indicator.setImageDrawable(ContextCompat.getDrawable(context, fillingDrawable))
        } else {
            indicator.setImageDrawable(ContextCompat.getDrawable(context, emptyDrawable))
        }
        listView.add(indicator)
    }


    private fun correctSizeIndicator() {
        viewTreeObserver.addOnGlobalLayoutListener {
            val deltaSize = width / count
            val wrapParams = LayoutParams(deltaSize, deltaSize)
            listView.forEach {
                it.layoutParams = wrapParams
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        listView.forEach { addView(it) }
        orientation = HORIZONTAL
    }

    fun progress(progress: Int) {
        listView.forEachIndexed { index, view ->
            val drawable = if (progress > index) {
                ContextCompat.getDrawable(context, fillingDrawable)
            } else {
                ContextCompat.getDrawable(context, emptyDrawable)
            }
            view.setImageDrawable(drawable)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAllViews()
    }
}