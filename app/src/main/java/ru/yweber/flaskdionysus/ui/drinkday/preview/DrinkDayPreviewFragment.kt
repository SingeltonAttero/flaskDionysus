package ru.yweber.flaskdionysus.ui.drinkday.preview

import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */

class DrinkDayPreviewFragment : BaseFragment(R.layout.fragment_drink_day_preview) {

    private val viewModel by inject<DrinkDayPreviewViewModel>()

    override fun installModule(scope: Scope) {
        scope.installViewModel<DrinkDayPreviewViewModel>()
    }
}