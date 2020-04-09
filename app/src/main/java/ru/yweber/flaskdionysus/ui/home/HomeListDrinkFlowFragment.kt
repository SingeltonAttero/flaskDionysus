package ru.yweber.flaskdionysus.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.android.synthetic.main.fragment_home_list_drink.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.core.adapter.DrinksAdapter
import ru.yweber.flaskdionysus.core.adapter.state.DrinkItem
import ru.yweber.flaskdionysus.core.decorator.PaddingItemDecorator
import ru.yweber.flaskdionysus.core.view.scroll.HideFragmentScrollListener
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

    private val adapter: AsyncListDifferDelegationAdapter<DrinkItem> by lazy { DrinksAdapter().createAdapter() }

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
        val setHide = AnimatorSet()
        val setVisible = AnimatorSet()
        rvDrinks.addOnScrollListener(HideFragmentScrollListener(hide = {
            setVisible.cancel()
            val animate = ObjectAnimator.ofFloat(
                containerDrinkOnDay, View.TRANSLATION_Y,
                0F, -containerDrinkOnDay.height.toFloat()
            )
            val alpha = ObjectAnimator.ofFloat(containerDrinkOnDay, View.ALPHA, 1F, 0.9F)
            val alpha2 = ObjectAnimator.ofFloat(containerDrinkOnDay, View.ALPHA, 1F, 0.5F)
            setHide.playSequentially(alpha, animate, alpha2)
            setHide.interpolator = AccelerateInterpolator(2F)
            setHide.start()
        }, visible = {
            setHide.cancel()
            rvDrinks.smoothScrollToPosition(0)
            val animate = ObjectAnimator.ofFloat(
                containerDrinkOnDay, View.TRANSLATION_Y,
                -containerDrinkOnDay.height.toFloat(), 0F
            )
            val alpha = ObjectAnimator.ofFloat(containerDrinkOnDay, View.ALPHA, 0.5F, 1F)
            setVisible.playTogether(animate, alpha)
            setVisible.interpolator = DecelerateInterpolator(2F)
            setVisible.start()
        }))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private fun renderState(state: ListDrinkState) {
        adapter.items = state.listDrink
    }

    override fun onDestroyView() {
        rvDrinks.adapter = null
        super.onDestroyView()
    }

}