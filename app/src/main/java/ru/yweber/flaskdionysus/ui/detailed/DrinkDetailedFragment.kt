package ru.yweber.flaskdionysus.ui.detailed

import android.os.Bundle
import android.view.View
import coil.api.load
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_drink_detailed.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.core.adapter.DrinkDayDelegateAdapter
import ru.yweber.flaskdionysus.di.utils.PrimitiveWrapper
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.system.upperCaseTitleTab
import ru.yweber.flaskdionysus.ui.detailed.state.DrinkDetailedState
import toothpick.Scope
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject

/**
 * Created on 29.04.2020
 * @author YWeber */

class DrinkDetailedFragment : BaseFragment(R.layout.fragment_drink_detailed) {

    private val viewModel by inject<DrinkDetailedViewModel>()

    private val pageAdapter by lazy {
        DrinkDayDelegateAdapter(true).createAdapter {

        }
    }

    private var layoutMediator: TabLayoutMediator? = null


    override fun installModule(scope: Scope) {
        scope.installModules(module {
            val drinkId = arguments?.getInt(EXTRA_DRINK_ID, -1) ?: -1
            bind(PrimitiveWrapper::class.java).toInstance(PrimitiveWrapper(drinkId))
        })
        scope.installViewModel<DrinkDetailedViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.state, ::renderState)
        viewPagerDetailedDrinkDay.adapter = pageAdapter
        layoutMediator = TabLayoutMediator(tabLayoutDetailedDrinkDay, viewPagerDetailedDrinkDay) { tab, position ->
            when (position) {
                0 -> tab.text = upperCaseTitleTab(R.string.main_drink)
                1 -> tab.text = upperCaseTitleTab(R.string.formula)
                2 -> tab.text = upperCaseTitleTab(R.string.composition)
                3 -> tab.text = upperCaseTitleTab(R.string.tools)
            }
        }
        layoutMediator?.attach()
    }

    private fun renderState(state: DrinkDetailedState) {
        pageAdapter.items = state.listPage
        tvNameDrink.text = state.title
        tvNameDrinkEn.text = state.titleEn
        loveRatingIndicator.progress(state.rating)
        ivPreviewImage.load(state.imagePath)
    }


    override fun backPressed(): Boolean {
        viewModel.backTo()
        return true
    }

    override fun onDestroyView() {
        layoutMediator?.detach()
        layoutMediator = null
        viewPagerDetailedDrinkDay.adapter = null
        super.onDestroyView()
    }

    companion object {
        private const val EXTRA_DRINK_ID = "EXTRA_DRINK_ID"
        fun newInstance(drinkId: Int) = with(DrinkDetailedFragment()) {
            val bundle = Bundle()
            bundle.putInt(EXTRA_DRINK_ID, drinkId)
            arguments = bundle
            this
        }
    }

}