package ru.yweber.flaskdionysus.ui.drinkday

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_drink_day_preview.view.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.di.DrinkDayNestedHolder
import ru.yweber.flaskdionysus.di.DrinkDayNestedRouter
import ru.yweber.flaskdionysus.di.module.installNestedNavigation
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import ru.yweber.flaskdionysus.model.interactor.DrinkDayUseCase
import ru.yweber.flaskdionysus.model.repository.DrinkDayRepository
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.drinkday.preview.DrinkDayPreviewFragment
import ru.yweber.flaskdionysus.ui.drinkday.state.DrinkTheDayState
import toothpick.Scope
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */
private const val DURATION_ANIMATION = 800L

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

                val containerViewGroup = currentFragment?.view?.findViewById<ConstraintLayout>(R.id.container)
                if (containerViewGroup != null && (currentFragment is DrinkDayPreviewFragment)
                ) {
                    fragmentTransaction.apply {
                        addSharedElement(
                            containerViewGroup.ivPreviewDrinkDay,
                            containerViewGroup.ivPreviewDrinkDay.transitionName
                        )
                        addSharedElement(
                            containerViewGroup.tvTitle,
                            containerViewGroup.tvTitle.transitionName
                        )
                        addSharedElement(
                            containerViewGroup.tvNameDrink,
                            containerViewGroup.tvNameDrink.transitionName
                        )
                        addSharedElement(
                            containerViewGroup.fabSwipeDrinkDay,
                            containerViewGroup.fabSwipeDrinkDay.transitionName
                        )
                        addSharedElement(
                            containerViewGroup.tvStatusBadge,
                            containerViewGroup.tvStatusBadge.transitionName
                        )
                        addSharedElement(
                            containerViewGroup.ivStatusHot,
                            containerViewGroup.ivStatusHot.transitionName
                        )
                        addSharedElement(
                            containerViewGroup.ivStatusPuff,
                            containerViewGroup.ivStatusPuff.transitionName
                        )
                    }
                }
                fragmentTransaction.setReorderingAllowed(true)
            }

        }

    override fun installModule(scope: Scope) {
        scope.installModules(module {
            bind(DrinkDayUseCase::class.java).singleton()
            bind(DrinkDayRepository::class.java).singleton()
        })
        scope.installNestedNavigation<DrinkDayNestedRouter, DrinkDayNestedHolder>(HandleCiceroneNavigate.DRINK_DAY_NESTED_FLOW)
        scope.installViewModel<DrinkTheDayFlowViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.state, ::render)
        if (childFragmentManager.fragments.isEmpty()) {
            viewModel.startPreview()
        }

    }

    private fun render(state: DrinkTheDayState) {
    }


}