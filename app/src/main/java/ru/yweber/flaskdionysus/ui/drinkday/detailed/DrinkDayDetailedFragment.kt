package ru.yweber.flaskdionysus.ui.drinkday.detailed

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_drink_day_detailed.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.core.adapter.DrinkDayDelegateAdapter
import ru.yweber.flaskdionysus.core.adapter.state.DetailedComponentItemState
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.system.toast
import ru.yweber.flaskdionysus.ui.drinkday.detailed.state.DrinkDayDetailedState
import toothpick.Scope
import toothpick.ktp.delegate.inject
import java.util.*

/**
 * Created on 07.04.2020
 * @author YWeber */

class DrinkDayDetailedFragment : BaseFragment(R.layout.fragment_drink_day_detailed) {

    private val viewModel by inject<DrinkDayDetailedViewModel>()

    private val pageAdapter by lazy {
        DrinkDayDelegateAdapter().createAdapter(::clickComponentItem)
    }

    private fun clickComponentItem(item: DetailedComponentItemState) {
        toast(item.toString())
    }

    override fun installModule(scope: Scope) {
        scope.installViewModel<DrinkDayDetailedViewModel>()
    }

    private var layoutMediator: TabLayoutMediator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.state, ::renderState)
        viewPagerDetailedDrinkDay.adapter = pageAdapter
        layoutMediator = TabLayoutMediator(tabLayoutDetailedDrinkDay, viewPagerDetailedDrinkDay) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.aboutDrink).toUpperCase(Locale.getDefault())
                1 -> tab.text = getString(R.string.formula).toUpperCase(Locale.getDefault())
                2 -> tab.text = getString(R.string.tools).toUpperCase(Locale.getDefault())
            }
        }
        layoutMediator?.attach()
    }

    private fun renderState(state: DrinkDayDetailedState) {
        pageAdapter.items = state.pageItem
        tvNameDrink.text = state.drinkName
    }

    override fun onDestroyView() {
        layoutMediator?.detach()
        layoutMediator = null
        viewPagerDetailedDrinkDay.adapter = null
        super.onDestroyView()
    }

}