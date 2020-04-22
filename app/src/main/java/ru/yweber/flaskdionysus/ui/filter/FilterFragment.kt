package ru.yweber.flaskdionysus.ui.filter

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_filter.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.core.adapter.filter.FilterDelegateAdapter
import ru.yweber.flaskdionysus.core.decorator.PaddingItemDecorator
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.filter.state.FilterState
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 22.04.2020
 * @author YWeber */

class FilterFragment : BaseFragment(R.layout.fragment_filter) {

    private val viewModel by inject<FilterViewModel>()

    private val adapter by lazy {
        FilterDelegateAdapter().createAdapter(viewModel::removeChip, viewModel::openFilterDialog)
    }

    override fun installModule(scope: Scope) {
        scope.installViewModel<FilterViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFilter.adapter = adapter
        rvFilter.addItemDecoration(PaddingItemDecorator())
        subscribe(viewModel.state, ::renderState)
    }

    private fun renderState(state: FilterState) {
        adapter.items = state.filters
    }

    override fun backPressed(): Boolean {
        viewModel.backTo()
        return true
    }

}