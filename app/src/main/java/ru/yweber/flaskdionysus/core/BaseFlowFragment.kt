package ru.yweber.flaskdionysus.core

import androidx.annotation.LayoutRes
import ru.terrakok.cicerone.Navigator

/**
 * Created on 31.03.2020
 * @author YWeber */

abstract class BaseFlowFragment(@LayoutRes layoutRes: Int) : BaseFragment(layoutRes) {

    protected abstract val navigator: Navigator
    protected abstract val viewModel: BaseViewModel<*>

    override fun onResume() {
        super.onResume()
        viewModel.addNavigator(navigator)
    }

    override fun onPause() {
        viewModel.removeNavigator()
        super.onPause()
    }

}