package ru.yweber.flaskdionysus.ui.filter.chooser

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_loading.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseDialog
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.filter.chooser.state.ChooserState
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 16.04.2020
 * @author YWeber */

class ChooserDialog : BaseDialog(R.layout.dialog_loading) {

    private val viewModel by inject<ChooserViewModel>()

    override val rootContainer: ViewGroup
        get() = containerLoading

    override fun installModule(scope: Scope) {
        scope.installViewModel<ChooserViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.state, ::renderState)
    }

    private fun renderState(state: ChooserState) {
        tvTest.text = state.test
    }

}