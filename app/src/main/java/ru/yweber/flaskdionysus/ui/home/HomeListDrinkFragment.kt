package ru.yweber.flaskdionysus.ui.home

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_home_list_drink.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.core.adapter.DrinksAdapter
import ru.yweber.flaskdionysus.core.decorator.PaddingItemDecorator
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.home.state.ListDrinkState
import toothpick.ktp.delegate.inject

/**
 * Created on 31.03.2020
 * @author YWeber */

class HomeListDrinkFragment : BaseFragment(R.layout.fragment_home_list_drink) {

    private val viewModel by inject<HomeListDrinkViewModel>()
    private val adapter by lazy { DrinksAdapter().createAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        installViewModel<HomeListDrinkViewModel>(parentScope)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.state, ::renderState)
        rvDrinks.adapter = adapter
        rvDrinks.addItemDecoration(PaddingItemDecorator())
    }

    private fun renderState(listDrinkState: ListDrinkState) {
        adapter.items = listDrinkState.listDrink
    }

}