package ru.yweber.flaskdionysus.ui.app

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
}