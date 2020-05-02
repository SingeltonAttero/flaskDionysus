package ru.yweber.flaskdionysus.ui.detailed

import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 29.04.2020
 * @author YWeber */

class DrinkDetailedFragment : BaseFragment(R.layout.fragment_drink_detailed) {

    private val viewModel by inject<DrinkDetailedViewModel>()

    override fun installModule(scope: Scope) {
        scope.installViewModel<DrinkDetailedViewModel>()
    }

    override fun backPressed(): Boolean {
        viewModel.backTo()
        return true
    }

}