package ru.yweber.flaskdionysus.ui.home.drinkday

import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */

class DrinkTheDayFragment : BaseFragment(R.layout.fragment_drink_the_day) {

    private val viewModel by inject<DrinkTheDayViewModel>()

    override fun installModule(scope: Scope) {
        scope.installViewModel<DrinkTheDayViewModel>()
    }

}