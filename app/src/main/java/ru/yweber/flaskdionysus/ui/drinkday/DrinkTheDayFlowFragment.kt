package ru.yweber.flaskdionysus.ui.drinkday

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_drink_the_day.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.di.DrinkDayNestedHolder
import ru.yweber.flaskdionysus.di.DrinkDayNestedRouter
import ru.yweber.flaskdionysus.di.module.installNestedNavigation
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.drinkday.state.DrinkTheDayState
import toothpick.Scope
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
        subscribe(viewModel.state, ::render)
        if (childFragmentManager.fragments.isEmpty()) {
            viewModel.startPreview()
        }
        fabSwipeDrinkDay.setOnClickListener {
            viewModel.swipePreviewToDetailed()
        }
    }

    private fun render(state: DrinkTheDayState) {
        val layoutParams = fabSwipeDrinkDay.layoutParams as CoordinatorLayout.LayoutParams
        val animatorSet = AnimatorSet()
        if (state.isPreview) {
            val translationY = ObjectAnimator.ofFloat(
                fabSwipeDrinkDay,
                View.TRANSLATION_Y,
                negativeDeltaHeight(layoutParams),
                0F
            )
            val rotation = ObjectAnimator.ofFloat(fabSwipeDrinkDay, View.ROTATION, 180F, 0F)
            animatorSet.playSequentially(translationY, rotation)
        } else {
            val translationY = ObjectAnimator.ofFloat(
                fabSwipeDrinkDay,
                View.TRANSLATION_Y,
                0F,
                negativeDeltaHeight(layoutParams)
            )
            val rotation = ObjectAnimator.ofFloat(fabSwipeDrinkDay, View.ROTATION, 0F, 180F)
            animatorSet.playSequentially(translationY, rotation)
        }
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.duration = 500
        animatorSet.start()
    }

    private fun negativeDeltaHeight(layoutParams: CoordinatorLayout.LayoutParams) =
        -(containerFlowFragment.height - (fabSwipeDrinkDay.height + layoutParams.topMargin + layoutParams.bottomMargin)).toFloat()
}