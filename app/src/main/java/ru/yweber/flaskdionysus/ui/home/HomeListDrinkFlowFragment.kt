package ru.yweber.flaskdionysus.ui.home

import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_home_list_drink.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.core.adapter.page.DrinksPageDelegateAdapter
import ru.yweber.flaskdionysus.core.decorator.PaddingItemDecorator
import ru.yweber.flaskdionysus.di.DrinkDayHolder
import ru.yweber.flaskdionysus.di.DrinkDayRouter
import ru.yweber.flaskdionysus.di.module.installNestedNavigation
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.home.state.ListDrinkState
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 31.03.2020
 * @author YWeber */

class HomeListDrinkFlowFragment : BaseFlowFragment(R.layout.fragment_home_list_drink) {

    private val adapter by lazy {
        DrinksPageDelegateAdapter().createPageAdapter()
    }

    override val viewModel by inject<HomeListDrinkViewModel>()

    override val navigator: Navigator
        get() = object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.containerDrinkOnDay) {
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
        scope.installNestedNavigation<DrinkDayRouter, DrinkDayHolder>(HandleCiceroneNavigate.DRINK_DAY_NAVIGATION)
        scope.installViewModel<HomeListDrinkViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            viewModel.navigateDrinkDay()
        }
        subscribe(viewModel.state, ::renderState)
        rvDrinks.adapter = adapter
        rvDrinks.addItemDecoration(PaddingItemDecorator())
    }

    private fun renderState(state: ListDrinkState) {
        if (state.isLoad) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                TransitionManager.endTransitions(rootContainer)
            }
            val slideTop = Slide(Gravity.TOP).apply {
                duration = 700
                startDelay = 100
                interpolator = AccelerateDecelerateInterpolator()
            }
            val slideBottom = Slide(Gravity.BOTTOM).apply {
                duration = 700
                startDelay = 100
                interpolator = AccelerateDecelerateInterpolator()
            }
            TransitionManager.beginDelayedTransition(appBarHomeList, slideTop)
            appBarHomeList.isInvisible = false
            TransitionManager.beginDelayedTransition(rootContainer, slideBottom)
            rootContainer.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))
            rvDrinks.isVisible = true
            fabFilter.isVisible = true
        } else {
            adapter.submitList(state.listDrink)
        }

    }


    override fun onDestroyView() {
        rvDrinks.adapter = null
        super.onDestroyView()
    }

}