package ru.yweber.flaskdionysus.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        DrinksPageDelegateAdapter().createPageAdapter(viewModel::navigateToDetailed)
    }

    private val expendMainAnimatorSet by lazy { AnimatorSet() }
    private val hideMainAnimatorSet by lazy { AnimatorSet() }
    private val expendInterpolator by lazy { AccelerateDecelerateInterpolator() }
    private val hideInterpolator by lazy { DecelerateInterpolator() }

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
        fabMain.setOnClickListener {
            viewModel.expendMenu()
        }
        smallFabSetting.setOnClickListener { viewModel.navigateSetting() }
        smallFabFilter.setOnClickListener { viewModel.navigateFilter() }
        smallFabSearch.setOnClickListener { viewModel.navigateSetting() }
    }

    private fun renderState(state: ListDrinkState) {
        if (state.isLoad) {
            containerAnimation()
        } else {
            adapter.submitList(state.listDrink)
        }
        if (state.filterDone) {
            adapter.submitList(state.listDrink)
        }
        if (state.animationFab) {
            menuSwap(state.menuExpend)
        }
    }

    private fun containerAnimation(duration: Long = 700, delay: Long = 100) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TransitionManager.endTransitions(rootContainer)
        }
        val slideTop = Slide(Gravity.TOP).apply {
            this.duration = duration
            startDelay = delay
            interpolator = expendInterpolator
        }
        val slideBottom = Slide(Gravity.BOTTOM).apply {
            this.duration = duration
            startDelay = delay
            interpolator = expendInterpolator
        }
        TransitionManager.beginDelayedTransition(appBarHomeList, slideTop)
        appBarHomeList.isInvisible = false
        TransitionManager.beginDelayedTransition(rootContainer, slideBottom)
        rootContainer.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))
        rvDrinks.isVisible = true
        fabMain.isVisible = true
        smallFabSetting.isVisible = true
        smallFabFilter.isVisible = true
        smallFabSearch.isVisible = true
    }

    private fun menuSwap(menuExpend: Boolean) {
        smallFabSetting.isEnabled = menuExpend
        smallFabSearch.isEnabled = menuExpend
        smallFabFilter.isEnabled = menuExpend
        if (menuExpend) {
            expendAnimation()
        } else {
            hideAnimation(170)
        }
    }

    private fun expendAnimation(duration: Long = 300) {
        hideMainAnimatorSet.cancel()
        val fabAbout = smallFabSetting.createAnimation(
            0F, -170F,
            0F, 20F,
            duration,
            true
        )
        val fabSearch = smallFabSearch.createAnimation(
            0F, 20F,
            0F, -170F,
            duration,
            true
        )
        val fabFilter = smallFabFilter.createAnimation(
            0F, -120F,
            0F, -120F,
            duration,
            true
        )
        expendMainAnimatorSet.playTogether(fabFilter, fabSearch, fabAbout)
        expendMainAnimatorSet.interpolator = expendInterpolator
        expendMainAnimatorSet.start()
    }

    private fun hideAnimation(duration: Long = 200) {
        expendMainAnimatorSet.cancel()
        val fabAbout = smallFabSetting.createAnimation(
            -170F, 0F,
            20F, 0F,
            duration
        )
        val fabSearch = smallFabSearch.createAnimation(
            20F, 0F,
            -170F, 0F,
            duration
        )
        val fabFilter = smallFabFilter.createAnimation(
            -120F, 0F,
            -120F, 0F,
            duration
        )
        hideMainAnimatorSet.playSequentially(fabAbout, fabSearch, fabFilter)
        hideMainAnimatorSet.interpolator = hideInterpolator
        hideMainAnimatorSet.start()
    }

    private fun FloatingActionButton.createAnimation(
        startX: Float,
        endX: Float,
        startY: Float,
        endY: Float,
        duration: Long,
        snake: Boolean = false
    ): AnimatorSet {
        val animX = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, startX, endX)
        val animY = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, startY, endY)
        val translationSet = AnimatorSet()
        translationSet.playTogether(animX, animY)

        val finishSet = AnimatorSet()
        if (snake) {
            createSnakeAnimate(finishSet, translationSet, duration)
        } else {
            finishSet.playSequentially(translationSet)
            finishSet.duration = duration
        }
        return finishSet
    }

    private fun FloatingActionButton.createSnakeAnimate(
        finishSet: AnimatorSet,
        translationSet: AnimatorSet,
        duration: Long
    ) {
        val rotateStart = ObjectAnimator.ofFloat(this, View.ROTATION, 0F, 45F)
        val rotateNormalizeA = ObjectAnimator.ofFloat(this, View.ROTATION, 45F, -30F)
        val rotateNormalizeB = ObjectAnimator.ofFloat(this, View.ROTATION, -30F, 20F)
        val rotateNormalizeC = ObjectAnimator.ofFloat(this, View.ROTATION, 20F, -10F)
        val rotateEnd = ObjectAnimator.ofFloat(this, View.ROTATION, -10F, 0F)
        val rotateSet = AnimatorSet()
        rotateSet.playSequentially(
            rotateStart, rotateNormalizeA, rotateNormalizeB, rotateNormalizeC, rotateEnd
        )
        rotateSet.duration = 100
        finishSet.playSequentially(translationSet, rotateSet)
        finishSet.duration = duration - 100
    }


    override fun onDestroyView() {
        rvDrinks.adapter = null
        super.onDestroyView()
    }

}