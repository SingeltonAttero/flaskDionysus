package ru.yweber.flaskdionysus.ui.drinkday.about

import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
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

}