package ru.yweber.flaskdionysus.ui.drinkday

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import coil.api.load
import kotlinx.android.synthetic.main.fragment_drink_day_preview.*
import kotlinx.android.synthetic.main.fragment_drink_the_day.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.di.AppScope
import ru.yweber.flaskdionysus.di.DrinkDayNestedHolder
import ru.yweber.flaskdionysus.di.DrinkDayNestedRouter
import ru.yweber.flaskdionysus.di.module.installNestedNavigation
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import toothpick.Scope
import toothpick.Toothpick
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */

class DrinkTheDayFlowFragment : BaseFlowFragment(R.layout.fragment_drink_the_day) {

    override val viewModel by inject<DrinkTheDayFlowViewModel>()
    override val navigator: Navigator
        get() = object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.rootFlowDrinkDayContainer) {
            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun installModule(scope: Scope) {
        scope.installNestedNavigation<DrinkDayNestedRouter, DrinkDayNestedHolder>(HandleCiceroneNavigate.DRINK_DAY_NESTED_FLOW)
        scope.installViewModel<DrinkTheDayFlowViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()){
            viewModel.startPreview()
        }
        fabSwipeDrinkDay.setOnClickListener {
            viewModel.swipePreviewToDetailed()
        }
    }
}