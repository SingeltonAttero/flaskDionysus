package ru.yweber.flaskdionysus.ui.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_loading.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseDialog

/**
 * Created on 16.04.2020
 * @author YWeber */

class LoadingDialog : BaseDialog(R.layout.dialog_loading) {
    override val rootContainer: ViewGroup
        get() = containerLoading

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}