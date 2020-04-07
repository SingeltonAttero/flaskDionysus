package ru.yweber.flaskdionysus.ui.drinkday.tools

import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */

class ToolsDrinkFragment : BaseFragment(R.layout.fragment_tools_drink) {

    private val viewModel by inject<ToolsDrinkViewModel>()

    override fun installModule(scope: Scope) {
        scope.installViewModel<ToolsDrinkViewModel>()
    }
}