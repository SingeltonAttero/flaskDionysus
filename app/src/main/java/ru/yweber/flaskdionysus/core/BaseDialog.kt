package ru.yweber.flaskdionysus.core

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import ru.yweber.flaskdionysus.R

/**
 * Created on 16.04.2020
 * @author YWeber */

abstract class BaseDialog(@LayoutRes private val layout: Int) : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDialogSize()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DownloadDialog)
    }

    abstract val rootContainer: ViewGroup

    internal open fun setDialogSize(percentHor: Float = 0.10F, percentVer: Float = 0.20F) {
        val defaultDisplay = requireActivity().windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        rootContainer.setPadding(
            (point.y * percentHor).toInt(),
            (point.x * percentVer).toInt(),
            (point.y * percentHor).toInt(),
            (point.x * percentVer).toInt()
        )
    }

}