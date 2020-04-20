package ru.yweber.flaskdionysus.ui.drinkday.preview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.transition.TransitionInflater
import coil.api.load
import kotlinx.android.synthetic.main.fragment_drink_day_preview.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.drinkday.preview.state.DrinkDayPreviewState
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        subscribe(viewModel.state, ::renderState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_animate)
    }

    private fun renderState(state: DrinkDayPreviewState) {
        ivPreviewDrinkDay.load(state.imagePath) {
            listener { data, source ->
                (view?.parent as ViewGroup?)?.doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
        }
        tvTitle.text = state.title
        tvNameDrink.text = state.drinkName
        lriDrinkDay.progress(state.rating)
        tvTriedDrink.text = state.checks
        tvCookingLevel.text = state.levelCooking
        tvAlcoholStrength.text = state.alcoholStrength

    }
}