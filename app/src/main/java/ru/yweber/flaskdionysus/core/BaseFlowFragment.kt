package ru.yweber.flaskdionysus.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import ru.terrakok.cicerone.Navigator

/**
 * Created on 31.03.2020
 * @author YWeber */

abstract class BaseFlowFragment(@LayoutRes layoutRes: Int) : BaseFragment(layoutRes) {

    protected abstract val navigator: Navigator
    protected abstract val viewModel: BaseViewModel<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.addNavigator(navigator)
    }

    override fun onPause() {
        viewModel.removeNavigator()
        super.onPause()
    }

}