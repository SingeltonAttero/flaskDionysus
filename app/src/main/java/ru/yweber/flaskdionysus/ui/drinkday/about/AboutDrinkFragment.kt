package ru.yweber.flaskdionysus.ui.drinkday.about

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.fragment_about_drink.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.drinkday.about.state.AboutDrinkState
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */

class AboutDrinkFragment : BaseFragment(R.layout.fragment_about_drink) {

    private val viewModel by inject<AboutDrinkViewModel>()

    override fun installModule(scope: Scope) {
        scope.installViewModel<AboutDrinkViewModel>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribe(viewModel.state, ::renderState)
    }

    private fun renderState(state: AboutDrinkState) {

    }

}