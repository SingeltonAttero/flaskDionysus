package ru.yweber.flaskdionysus.ui.home

import android.os.Bundle
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import toothpick.ktp.delegate.inject

/**
 * Created on 31.03.2020
 * @author YWeber */

class HomeListDrinkFragment : BaseFragment(R.layout.fragment_home_list_drink) {

    private val viewModel by inject<HomeListDrinkViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installViewModel<HomeListDrinkViewModel>(parentScope)
        super.onCreate(savedInstanceState)
    }

}