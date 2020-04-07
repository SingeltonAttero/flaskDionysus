package ru.yweber.flaskdionysus.ui.drinkday

import android.os.Bundle
import android.view.View
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.di.DrinkDayNestedHolder
import ru.yweber.flaskdionysus.di.DrinkDayNestedRouter
import ru.yweber.flaskdionysus.di.module.installNestedNavigation
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */

class DrinkTheDayFlowFragment : BaseFlowFragment(R.layout.fragment_drink_the_day) {

    override val viewModel by inject<DrinkTheDayFlowViewModel>()
    override val navigator: Navigator
        get() = SupportAppNavigator(requireActivity(), childFragmentManager, R.id.rootFlowDrinkDayContainer)

    override fun installModule(scope: Scope) {
        scope.installNestedNavigation<DrinkDayNestedRouter, DrinkDayNestedHolder>(HandleCiceroneNavigate.DRINK_DAY_NESTED_FLOW)
        scope.installViewModel<DrinkTheDayFlowViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startPreview()
    }
}