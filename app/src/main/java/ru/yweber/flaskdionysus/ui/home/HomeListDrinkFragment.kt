package ru.yweber.flaskdionysus.ui.home

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.android.synthetic.main.fragment_home_list_drink.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.core.adapter.DrinksAdapter
import ru.yweber.flaskdionysus.core.adapter.state.DrinkItem
import ru.yweber.flaskdionysus.core.decorator.PaddingItemDecorator
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.home.state.ListDrinkState
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 31.03.2020
 * @author YWeber */

class HomeListDrinkFragment : BaseFragment(R.layout.fragment_home_list_drink) {

    private val viewModel by inject<HomeListDrinkViewModel>()
    private val adapter: AsyncListDifferDelegationAdapter<DrinkItem> by lazy { DrinksAdapter().createAdapter() }

    override fun installModule(scope: Scope) {
        scope.installViewModel<HomeListDrinkViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.state, ::renderState)
        rvDrinks.adapter = adapter
        rvDrinks.addItemDecoration(PaddingItemDecorator())
        childFragmentManager.beginTransaction()
            .replace(R.id.containerDrinkOnDay, Screens.DrinkTheDayScreen.fragment)
            .commit()
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